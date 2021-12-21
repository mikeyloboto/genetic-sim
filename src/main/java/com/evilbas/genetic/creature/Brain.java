package com.evilbas.genetic.creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ObjectUtils;

import lombok.Data;

@Data
public class Brain implements Cloneable {
    private static final Integer MAX_CONNECTIONS = 100;
    private static final Integer INIT_CONNECTIONS = 10;
    private static final Integer MID_LAYER = 10;
    private static final Integer CONNECTION_GEN_RETRY = 5;
    // Inputs
    private Neuron inputEast;
    private Neuron inputWest;
    private Neuron inputNorth;
    private Neuron inputSouth;

    private Neuron inputRandom;
    private Neuron inputHunger;

    // Hidden
    private List<Neuron> hiddenNeurons;

    // Outputs

    private Neuron outputNorth;
    private Neuron outputSouth;
    private Neuron outputEast;
    private Neuron outputWest;
    private Neuron outputEat;

    // Collections

    private List<Neuron> viableInputs;
    private List<Neuron> viableOutputs;
    private List<Connection> connections;

    private static Random random;

    private Brain() {

        this.viableInputs = new ArrayList<>();
        this.viableOutputs = new ArrayList<>();
        this.hiddenNeurons = new ArrayList<>();

        this.inputEast = Neuron.builder().type(NeuronType.INPUT).build();
        this.inputWest = Neuron.builder().type(NeuronType.INPUT).build();
        this.inputNorth = Neuron.builder().type(NeuronType.INPUT).build();
        this.inputSouth = Neuron.builder().type(NeuronType.INPUT).build();
        this.inputRandom = Neuron.builder().type(NeuronType.INPUT).build();
        this.inputHunger = Neuron.builder().type(NeuronType.INPUT).build();

        this.viableInputs.add(inputEast);
        this.viableInputs.add(inputWest);
        this.viableInputs.add(inputNorth);
        this.viableInputs.add(inputSouth);
        this.viableInputs.add(inputRandom);
        this.viableInputs.add(inputHunger);

        for (int i = 0; i < MID_LAYER; i++) {
            Neuron mid = Neuron.builder().type(NeuronType.HIDDEN).build();
            this.hiddenNeurons.add(mid);
            this.viableInputs.add(mid);
            this.viableOutputs.add(mid);
        }

        this.outputEast = Neuron.builder().type(NeuronType.OUTPUT).build();
        this.outputWest = Neuron.builder().type(NeuronType.OUTPUT).build();
        this.outputNorth = Neuron.builder().type(NeuronType.OUTPUT).build();
        this.outputSouth = Neuron.builder().type(NeuronType.OUTPUT).build();
        this.outputEat = Neuron.builder().type(NeuronType.OUTPUT).build();

        this.viableOutputs.add(outputEast);
        this.viableOutputs.add(outputWest);
        this.viableOutputs.add(outputNorth);
        this.viableOutputs.add(outputSouth);
        this.viableOutputs.add(outputEat);

        this.connections = new ArrayList<>();

    }

    public Brain clearOutputs() {
        // Clear
        for (Neuron n : this.viableOutputs) {
            n.setValue(0);
        }
        return this;
    }

    public void processStep() {

        if (random == null) {
            random = new Random();
        }

        // Clear
        this.clearOutputs();

        // Generate Randomness
        this.inputRandom.setValue(random.nextInt(2000) - 1000);

        // Calc
        Collections.sort(this.connections);

        List<Connection> stepConnections = new ArrayList<>();

        for (Connection c : this.connections) {
            processConnectionRecursive(c.getInput(), stepConnections);
        }
    }

    private void processConnectionRecursive(Neuron n, List<Connection> scanned) {
        for (Connection c : this.connections) {
            if (c.getInput() == n) {
                if (scanned.contains(c)) {
                    break;
                } else {
                    scanned.add(c);
                    c.procConnection();
                    processConnectionRecursive(c.getOutput(), scanned);
                }
            }
        }
    }

    private String mapConnectionsRecursive(Neuron n, List<Connection> scanned, Boolean first) {
        StringBuilder output = new StringBuilder();
        for (Connection c : this.connections) {
            if (c.getInput() == n) {
                if (scanned.contains(c)) {
                    break;
                } else {
                    scanned.add(c);
                    if (first) {
                        output.append(c.getInput().toString());
                    }
                    output.append(" -> ");
                    output.append(c.getMod());
                    output.append(" -> ");
                    output.append(c.getOutput().toString());

                    output.append(mapConnectionsRecursive(c.getOutput(), scanned, false));
                }
            }
        }

        return output.toString();
    }

    public static Brain mutate(Brain origin) {
        if (random == null) {
            random = new Random();
        }
        Brain newBrain = ObjectUtils.clone(origin);
        newBrain.clearOutputs();
        int choice = random.nextInt(10);
        // 10% Chance to generate a new neural connection
        // 30% each to change input/output neuron or connection modifier
        switch (choice) {
            case 0:
                newBrain.getConnections().add(newBrain.generateConnection());
                break;
            case 1:
            case 2:
            case 3:
                newBrain.getConnections().get(random.nextInt(newBrain.getConnections().size()))
                        .setMod(random.nextInt(2000) - 1000);
                break;
            case 4:
            case 5:
            case 6:
                newBrain.getConnections().get(random.nextInt(newBrain.getConnections().size())).setInput(
                        newBrain.getViableInputs().get(random.nextInt(newBrain.getViableInputs().size())));
                break;
            case 7:
            case 8:
            case 9:
                newBrain.getConnections().get(random.nextInt(newBrain.getConnections().size())).setOutput(
                        newBrain.getViableOutputs().get(random.nextInt(newBrain.getViableOutputs().size())));
                break;
        }

        return newBrain;
    }

    public String mapBrain() {
        StringBuilder sb = new StringBuilder();
        List<Connection> stepConnections = new ArrayList<>();
        for (Connection c : this.connections) {
            String out = mapConnectionsRecursive(c.getInput(), stepConnections, true);
            if (out != null && !out.equals(""))
                sb.append(out);
            sb.append('\n');
        }
        return sb.toString();
    }

    public static Brain generateRandom() {
        Brain result = new Brain();
        for (int i = 0; i < INIT_CONNECTIONS; i++) {
            result.getConnections().add(result.generateConnection());
        }
        return result;
    }

    public Boolean duplicateConnection(Connection check) {
        for (Connection c : connections) {
            if (c.getInput() == check.getInput() && c.getOutput() == check.getOutput()) {
                return true;
            }
        }
        return false;
    }

    private Connection generateConnection() {
        if (random == null) {
            random = new Random();
        }
        Connection result = null;
        boolean regen = true;
        int retry = 0;
        while (regen) {
            result = Connection.builder().input(viableInputs.get(random.nextInt(viableInputs.size())))
                    .output(viableOutputs.get(random.nextInt(viableOutputs.size()))).mod(random.nextInt(2000) - 1000)
                    .build();
            if (!this.duplicateConnection(result))
                regen = false;
            if (retry >= CONNECTION_GEN_RETRY)
                regen = false;
        }
        return result;
    }

    @Override
    public Brain clone() {
        try {
            return (Brain) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
