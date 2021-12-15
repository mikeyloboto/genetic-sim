package com.evilbas.genetic.creature;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Brain {
    private static final Integer MAX_CONNECTIONS = 100;
    private static final Integer INIT_CONNECTIONS = 10;
    private static final Integer MID_LAYER = 10;
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

    public void processStep() {
        // Clear
        for (Neuron n : this.viableOutputs) {
            n.setValue(0);
        }

        // Calc
    }

    public static Brain mutate(Brain origin) {
        try {
            return (Brain) origin.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Brain generateRandom() {
        Brain result = new Brain();

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

}
