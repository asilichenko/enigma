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

import ua.in.asilichenko.enigma.hillclimbing.entity.AbstractEnigma;
import ua.in.asilichenko.enigma.hillclimbing.result.PlugboardResult;
import ua.in.asilichenko.enigma.setting.IndicatorsSetting;

import static ua.in.asilichenko.enigma.hillclimbing.config.GeneralConfig.*;
import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;

/**
 * Creation date: 12.11.2022
 */
public class ComputeService<E extends AbstractEnigma> {

    @SuppressWarnings("WeakerAccess")
    PlugboardService plugboardService;

    public void setPlugboardService(PlugboardService plugboardService) {
        this.plugboardService = plugboardService;
    }

    /**
     * Precompute full alphabet on each step of text length.
     * <p>
     * Indicators must be adjusted in advance.
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
     *
     * @param enigma Enigma machine
     * @param count  length of the text
     * @return precomputed
     */
    int[][] precompute(E enigma, int count) {
        final int[][] retval = new int[count][LETTERS_COUNT];

        for (int step = 0; step < count; step++) {
            enigma.rotate();
            final int[] state = retval[step];
            for (int letter = 0; letter < LETTERS_COUNT; letter++) {
                state[letter] = enigma.cipher(letter);
            }
        }

        return retval;
    }

    private long calcScore(E enigma, int[] text) {
        return IOC_FITNESS.score(enigma.cipher(text));
    }

    public PlugboardResult compute(int[] text, E enigma, IndicatorsSetting indicatorsSetting, long filterThreshold) {
        final long score = calcScore(enigma, text);
        if (score < filterThreshold) return null;

        enigma.adjustIndicators(indicatorsSetting.getIndicators());
        final int[][] precomputed = precompute(enigma, text.length);

        return plugboardService.findBestPlugboard(text, precomputed,
                PLUGBOARD_FIRST_STAGE_FITNESS,
                PLUGBOARD_MID_STAGE_FITNESS,
                PLUGBOARD_LAST_STAGE_FITNESS);
    }
}
