package com.evilbas.genetic;

import com.evilbas.genetic.creature.Creature;

/**
 * Hello world!
 *
 */
public class Launcher {
    public static void main(String[] args) {
        Creature test = Creature.generateRandomCreature();
        System.out.println(test.toString());
    }
}
