package ua.in.asilichenko.enigma.util;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 13.11.2022
 */
public class RotorUtils {

    /**
     * Reverse rotor wiring form one side to the other.
     *
     * @param wiring forward wiring
     * @return backward wiring
     */
    public static int[] reverse(int[] wiring) {
        final int[] retval = new int[wiring.length];
        for (int backwardLetter = 0; backwardLetter < retval.length; backwardLetter++) {
            final int forwardLetter = wiring[backwardLetter];
            retval[forwardLetter] = backwardLetter;
        }
        return retval;
    }
}
