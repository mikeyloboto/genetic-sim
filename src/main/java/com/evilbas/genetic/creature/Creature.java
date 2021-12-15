package com.evilbas.genetic.creature;

import java.util.Random;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Creature {
    // neural net
    private Brain brain;
    private Long age;
    private Integer hunger;
    private Boolean dead;

    private static Random random;

    public void incrementAge() {
        if (!this.getDead()) {
            this.age++;
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

    public static Creature generateRandomCreature() {
        return Creature.builder().age(0L).hunger(1000).brain(Brain.generateRandom()).build();
    }

    public static Creature generateChildCreature(Creature parent) {
        if (random == null) {
            random = new Random();
        }
        Creature result = null;
        try {
            result = (Creature) parent.clone();
            if (random.nextInt(1000) == 0) {

            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
