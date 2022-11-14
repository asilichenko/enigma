package ua.in.asilichenko.enigma.service;

import ua.in.asilichenko.enigma.domain.Plugboard;

import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.connect;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.isOccupied;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 07.10.2022
 */
public class PlugboardService {

    private static final int PLUG_LEN = 2;
    private static final int MAX_PLUGS_COUNT = LETTERS_COUNT / PLUG_LEN;

    private static final int PLUG_A = 0;
    private static final int PLUG_B = 1;

    public Plugboard createPlugboard(String[] plugPairs) {
        final Plugboard retval = new Plugboard();
        adjustPlugboard(retval, plugPairs);
        return retval;
    }

    private void validatePlugPair(String plugPair) {
        if (PLUG_LEN != plugPair.length()) {
            throw new IllegalArgumentException("Plug pairs must contain 2 letters: " + plugPair);
        }
    }

    private void validatePlugPairs(String[] plugPairs) {
        if (plugPairs.length > MAX_PLUGS_COUNT) {
            throw new IllegalArgumentException("Plugs count must not excess max count: " + MAX_PLUGS_COUNT + ", but were: " + plugPairs.length);
        }
    }

    private void validatePlug(int[] plugboard, int letter) {
        if (isOccupied(plugboard, letter)) {
            throw new IllegalArgumentException("Each letter must be used only once: " + letter);
        }
    }

    private void validateLetter(int letter) {
        if (letter < 0 || letter >= LETTERS_COUNT) {
            throw new IllegalArgumentException(String.format("Incorrect letter: %d. Must be in range: [0, %d]", letter, LETTERS_COUNT - 1));
        }
    }

    public void connectPlugPair(Plugboard plugboard, String plugPair) {
        final int[] pair = indexOf(plugPair);
        connect(plugboard.getLayout(), pair[PLUG_A], pair[PLUG_B]);
    }

    public void adjustPlugboard(Plugboard plugboard, String[] plugPairs) {
        validatePlugPairs(plugPairs);

        final int[] layout = plugboard.getLayout();
        for (String plugPair : plugPairs) {
            validatePlugPair(plugPair);

            final int[] pair = indexOf(plugPair);

            final int plugA = pair[PLUG_A];
            final int plugB = pair[PLUG_B];

            validatePlug(layout, plugA);
            validatePlug(layout, plugB);

            connect(layout, plugA, plugB);
        }
    }

    public int cipher(Plugboard plugboard, int letter) {
        validateLetter(letter);
        return plugboard.getLayout()[letter];
    }
}
