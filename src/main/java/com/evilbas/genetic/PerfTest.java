package com.evilbas.genetic;

import com.evilbas.genetic.creature.Creature;

/**
 * Hello world!
 *
 */
public class PerfTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000; i++) {
            Creature.generateRandomCreature();
        }
        long end = System.currentTimeMillis() - start;

        // System.out.println(test.toString());
        System.out.println("Generation time: " + end + "ms. 1000 iterations.");

        Creature test = Creature.generateRandomCreature();
        test.setInputs(20, 300, 800, 150);
        long startStep = System.currentTimeMillis();

        for (int i = 0; i < 1_000; i++) {
            test.setInputs(20, 300, 800, 150);
            test.step();
            Creature child = Creature.generateChildCreature(test);
        }
        long endStep = System.currentTimeMillis() - startStep;

        System.out.println("Simulation time: " + endStep + "ms. 1 creature. 1000 steps.");
        System.out.println(test.toString());
    }
}
