package com.evilbas.genetic.creature;

import java.util.List;

import lombok.Data;

@Data
public class Brain {
    private static final Integer MAX_CONNECTIONS = 20;
    // Inputs
    private Neuron inputEast;
    private Neuron inputWest;
    private Neuron inputNorth;
    private Neuron inputSouth;

    private Neuron inputRandom;
    private Neuron inputHunger;
    private Neuron inputAge;

    // Hidden
    private List<Neuron> hiddenNeurons;

    // Outputs

    private Neuron outputNorth;
    private Neuron outputSouth;
    private Neuron outputEast;
    private Neuron outputWest;
    private Neuron outputEat;

    public static Brain mutate(Brain origin) {
        try {
            return (Brain) origin.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Brain generateRandom() {
        return null;
    }
}
