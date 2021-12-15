package com.evilbas.genetic.creature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Neuron {
    private Integer value;
    private NeuronType type;
}
