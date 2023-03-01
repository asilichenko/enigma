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

/**
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
