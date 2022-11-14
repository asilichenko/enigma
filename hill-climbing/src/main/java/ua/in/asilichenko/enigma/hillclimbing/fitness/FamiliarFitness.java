package ua.in.asilichenko.enigma.hillclimbing.fitness;

/**
 * Compute score that is a number of same letters between sample and test.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 11.11.2022
 */
@SuppressWarnings("unused")
public class FamiliarFitness extends FitnessFunction {

    private final int[] sample;

    public FamiliarFitness(int[] sample) {
        this.sample = sample;
    }

    @Override
    public long score(int[] text) {
        int retval = 0;
        for (int i = 0; i < sample.length; i++) if (sample[i] == text[i]) retval++;
        return retval;
    }
}
