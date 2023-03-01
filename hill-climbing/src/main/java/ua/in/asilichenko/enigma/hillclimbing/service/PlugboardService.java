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

import ua.in.asilichenko.enigma.hillclimbing.result.PlugboardResult;
import ua.in.asilichenko.enigma.hillclimbing.fitness.FitnessFunction;
import ua.in.asilichenko.enigma.util.PlugboardUtils;

import static ua.in.asilichenko.enigma.hillclimbing.config.GeneralConfig.PLUG_PAIRS_IN_USE;
import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.*;

/**
 * Creation date: 04.11.2022
 */
public class PlugboardService {

    private static final int MIDDLE_STAGE_THRESHOLD = 4;
    private static final int LAST_STAGE_THRESHOLD = 9;

    // could be overridden
    @SuppressWarnings({"unused", "WeakerAccess"})
    int[] createPlugboard() {
        return PlugboardUtils.createPlugboard();
    }

    // could be overridden
    @SuppressWarnings({"unused", "WeakerAccess"})
    int pairsConnectedNumber() {
        return 0;
    }

    /**
     * <pre>
     *  precomputed: [
     *        A    B    C    D
     *      ['X', 'Q', 'P', 'T', ...], // step 0
     *      ['V', 'H', 'A', 'N', ...], // step 1
     *  ]
     *
     *  'A' at step 0 is enciphered as 'X'
     *  'B' at step 0 is enciphered as 'Q'
     *  ...
     *  'A' at step 1 is enciphered as 'V'
     *  'B' at step 1 is enciphered as 'H'
     *  ...
     *  </pre>
     */
    public PlugboardResult findBestPlugboard(int[] text,
                                             int[][] precomputed,
                                             FitnessFunction firstStageFitness,
                                             FitnessFunction midStageFitness,
                                             FitnessFunction lastStageFitness) {
        final int[] plugboard = createPlugboard();
        Long bestScore = null;

        for (int plugCnt = pairsConnectedNumber(); plugCnt < PLUG_PAIRS_IN_USE; plugCnt++) {
            final FitnessFunction fitnessFunction;
            if (plugCnt < MIDDLE_STAGE_THRESHOLD) fitnessFunction = firstStageFitness;
            else if (plugCnt < LAST_STAGE_THRESHOLD) fitnessFunction = midStageFitness;
            else fitnessFunction = lastStageFitness;

            final PlugPairScore bestPlugPair = findBetterPlugPair(text, precomputed, plugboard, fitnessFunction);

            if (null == bestPlugPair) break;
            bestScore = bestPlugPair.score;
        }

        return null != bestScore ? new PlugboardResult(plugboard, bestScore) : null;
    }

    private class PlugPairScore {
        final int plugA, plugB;
        final long score;

        PlugPairScore(int plugA, int plugB, long score) {
            this.plugA = plugA;
            this.plugB = plugB;
            this.score = score;
        }
    }

    private boolean isBetter(long score, PlugPairScore plugPair) {
        return null == plugPair || score > plugPair.score;
    }

    //could be overridden
    @SuppressWarnings({"WeakerAccess", "unused"})
    boolean skipPlug(int plug) {
        return false;
    }

    private PlugPairScore findBetterPlugPair(int[] text, int[][] precomputed, int[] plugboard, FitnessFunction fitnessFunction) {
        PlugPairScore retval = null;
        for (int plugA = 0; plugA < LETTERS_COUNT - 1; plugA++) {
            if (isOccupied(plugboard, plugA) || skipPlug(plugA)) continue;

            for (int plugB = plugA + 1; plugB < LETTERS_COUNT; plugB++) {
                if (isOccupied(plugboard, plugB) || skipPlug(plugB)) continue;

                connect(plugboard, plugA, plugB);
                final long score = calcScore(text, precomputed, plugboard, fitnessFunction);
                release(plugboard, plugA, plugB);

                if (isBetter(score, retval)) retval = new PlugPairScore(plugA, plugB, score);
            }
        }
        if (null != retval) connect(plugboard, retval.plugA, retval.plugB);
        return retval;
    }

    private long calcScore(int[] text,
                           int[][] precomputed,
                           int[] plugboard,
                           FitnessFunction fitnessFunction) {
        final int[] enciphered = new int[text.length];
        for (int step = 0; step < text.length; step++) {
            int letter = text[step];
            letter = plugboard[letter];
            letter = precomputed[step][letter];
            letter = plugboard[letter];
            enciphered[step] = letter;
        }
        return fitnessFunction.score(enciphered);
    }
}
