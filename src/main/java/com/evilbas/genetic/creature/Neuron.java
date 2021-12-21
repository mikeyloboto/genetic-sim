package com.evilbas.genetic.creature;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Neuron {
    private Integer value;
    private NeuronType type;
    private String uuid;

    private Neuron(Integer value, NeuronType type, String uuid) {
        this.value = value;
        this.type = type;
        this.uuid = UUID.randomUUID().toString();
    }
}
