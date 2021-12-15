package com.evilbas.genetic.creature;

import java.util.Random;

import org.apache.commons.lang3.ObjectUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Creature implements Cloneable {
    // neural net
    private Brain brain;
    private Long age;
    private Integer hunger;
    private Boolean dead;

    private static Random random;

    public void step() {
        if (!this.getDead()) {
            this.age++;
            this.getBrain().processStep();
            this.hunger--;
            if (hunger == 0) {
                this.setDead(true);
            }
        }
    }

    public void feed() {
        if (!this.getDead()) {
            this.hunger = 1000;
        }
    }

    public void setInputs(Integer north, Integer south, Integer east, Integer west) {
        this.getBrain().getInputNorth().setValue(north);
        this.getBrain().getInputSouth().setValue(south);
        this.getBrain().getInputWest().setValue(west);
        this.getBrain().getInputEast().setValue(east);
        this.getBrain().getInputHunger().setValue(this.getHunger());
    }

    public static Creature generateRandomCreature() {
        return Creature.builder().dead(false).age(0L).hunger(1000).brain(Brain.generateRandom()).build();
    }

    public static Creature generateChildCreature(Creature parent) {
        if (random == null) {
            random = new Random();
        }
        Creature result = null;

        result = Creature.builder().dead(false).age(0L).hunger(1000)
                .brain(ObjectUtils.clone(parent.getBrain()).clearOutputs())
                .build();
        if (random.nextInt(1000) == 0) {
            result.setBrain(Brain.mutate(result.getBrain()));
            System.out.println("Mutation: " + result.toString());
        }

        return result;
    }
}
