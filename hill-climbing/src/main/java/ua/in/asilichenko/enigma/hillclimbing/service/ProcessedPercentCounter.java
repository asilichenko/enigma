package ua.in.asilichenko.enigma.hillclimbing.service;

import static com.google.common.math.BigIntegerMath.factorial;

/**
 * Service to calculate percentage of completed work.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 04.11.2022
 */
public class ProcessedPercentCounter {

    private int counter = 0;
    private final int totalCount;

    static int permutationCount(int reflectors, int totalRotors, int rotorsInUse) {
        return reflectors * factorial(totalRotors)
                .divide(factorial(totalRotors - rotorsInUse))
                .intValue();
    }

    public ProcessedPercentCounter(int reflectors, int totalRotors, int rotorsInUse) {
        this.totalCount = permutationCount(reflectors, totalRotors, rotorsInUse);
    }

    public float next() {
        return counter++ * 100f / totalCount;
    }
}
