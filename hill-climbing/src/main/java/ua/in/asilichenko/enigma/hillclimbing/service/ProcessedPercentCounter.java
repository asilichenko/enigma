/*
 *   Copyright (c) 2022 Oleksii Sylichenko.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ua.in.asilichenko.enigma.hillclimbing.service;

import static com.google.common.math.BigIntegerMath.factorial;

/**
 * Service to calculate percentage of completed work.
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
