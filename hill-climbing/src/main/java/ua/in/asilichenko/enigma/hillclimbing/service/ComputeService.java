package ua.in.asilichenko.enigma.hillclimbing.service;

import ua.in.asilichenko.enigma.hillclimbing.entity.AbstractEnigma;
import ua.in.asilichenko.enigma.hillclimbing.result.PlugboardResult;
import ua.in.asilichenko.enigma.setting.IndicatorsSetting;

import static ua.in.asilichenko.enigma.hillclimbing.config.GeneralConfig.*;
import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
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
