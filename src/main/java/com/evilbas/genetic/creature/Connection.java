package com.evilbas.genetic.creature;

import lombok.Data;

@Data
public class Connection implements Comparable<Connection> {
    private Neuron input;
    private Neuron output;
    private Integer mod;

    public void procConnection() {
        this.getOutput().setValue(this.getOutput().getValue() + (this.getInput().getValue() * this.getMod()));
    }

    public Integer compareValue() {
        if (this.getInput().getType() == NeuronType.INPUT && this.getOutput().getType() == NeuronType.OUTPUT)
            return 0;
        if (this.getInput().getType() == NeuronType.INPUT && this.getOutput().getType() == NeuronType.HIDDEN)
            return 1;
        if (this.getInput().getType() == NeuronType.HIDDEN && this.getOutput().getType() == NeuronType.HIDDEN)
            return 2;
        if (this.getInput().getType() == NeuronType.HIDDEN && this.getOutput().getType() == NeuronType.OUTPUT)
            return 3;
        return 4;
    }

    @Override
    public int compareTo(Connection o) {
        return o.compareValue() - this.compareValue();
    }
}
