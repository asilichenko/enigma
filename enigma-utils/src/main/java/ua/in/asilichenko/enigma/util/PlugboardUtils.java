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

package ua.in.asilichenko.enigma.util;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ua.in.asilichenko.enigma.util.LetterUtils.*;

/**
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
