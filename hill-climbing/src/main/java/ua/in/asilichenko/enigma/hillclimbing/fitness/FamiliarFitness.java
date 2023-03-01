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

package ua.in.asilichenko.enigma.hillclimbing.fitness;

/**
 * Compute score that is a number of same letters between sample and test.
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
