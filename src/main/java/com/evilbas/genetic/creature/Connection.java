package com.evilbas.genetic.creature;

import lombok.Data;

@Data
public class Connection {
    private Neuron input;
    private Neuron output;
    private Integer mod;

    public void procConnection() {
        this.getOutput().setValue(this.getInput().getValue() * this.getMod());
    }
}
