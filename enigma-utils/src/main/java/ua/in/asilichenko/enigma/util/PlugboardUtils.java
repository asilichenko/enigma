package ua.in.asilichenko.enigma.util;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ua.in.asilichenko.enigma.util.LetterUtils.*;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 06.11.2022
 */
public class PlugboardUtils {

    private static final int PLUG_A = 0;
    private static final int PLUG_B = 1;

    private static final int PAIR_STRING_SIZE = 3; // 1 extra char for ending space that will be trimmed
    private static final int PLUG_PAIRS_NUMBER = PAIR_STRING_SIZE * LETTERS_COUNT / 2;

    private static final int[] DEFAULT_PLUGBOARD = indexOf(ABC);

    public static int[] createPlugboard() {
        return Arrays.copyOf(DEFAULT_PLUGBOARD, DEFAULT_PLUGBOARD.length);
    }

    public static int[] plugboardWith(String[] plugPairs) {
        final int[] retval = createPlugboard();
        for (String pair : plugPairs) {
            final int[] plugs = indexOf(pair);
            connect(retval, plugs[PLUG_A], plugs[PLUG_B]);
        }
        return retval;
    }

    public static boolean isOccupied(int[] plugboard, int letter) {
        return letter != plugboard[letter];
    }

    public static void connect(int[] plugboard, int plugA, int plugB) {
        plugboard[plugA] = plugB;
        plugboard[plugB] = plugA;
    }

    public static void release(int[] plugboard, int plugA, int plugB) {
        plugboard[plugA] = plugA;
        plugboard[plugB] = plugB;
    }

    /**
     * Print string of connected pairs of letters
     * separated with space.
     *
     * @param plugboard plugboard layout
     * @return String like "CQ DU EN FR GX IS JP KO TY VZ"
     */
    public static String asString(int[] plugboard) {
        final CharBuffer retval = CharBuffer.allocate(PLUG_PAIRS_NUMBER);
        final Set<Integer> viewed = new HashSet<>();

        for (int plugA = 0; plugA < LETTERS_COUNT; plugA++) {
            if (!isOccupied(plugboard, plugA)) continue;

            viewed.add(plugA);

            final int plugB = plugboard[plugA];
            if (viewed.add(plugB)) {
                retval.append(letterOf(plugA)).append(letterOf(plugB)).append(' ');
            }
        }

        return retval.rewind().toString().trim();
    }
}
