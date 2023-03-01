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

package ua.in.asilichenko.enigma.hillclimbing.entity;

/**
 * Creation date: 12.11.2022
 */
public abstract class AbstractEnigma {

    private final int[] reflector;

    final Rotor leftRotor;
    final Rotor midRotor;
    final Rotor rightRotor;

    private final int[] plugboard;

    AbstractEnigma(int[] reflector, Rotor leftRotor, Rotor midRotor, Rotor rightRotor, int[] plugboard) {
        this.reflector = reflector;
        this.leftRotor = leftRotor;
        this.midRotor = midRotor;
        this.rightRotor = rightRotor;
        this.plugboard = plugboard;
    }

    public abstract void adjustIndicators(int[] indicators);

    public final void rotate() {
        if (midRotor.isInNotchPos()) {
            midRotor.rotate();
            leftRotor.rotate();
        } else if (rightRotor.isInNotchPos()) {
            midRotor.rotate();
        }
        rightRotor.rotate();
    }

    int cipherForwardExtra(int letter) {
        return letter;
    }

    int cipherBackwardExtra(int letter) {
        return letter;
    }

    public final int cipher(int letter) {
        if (null != plugboard) letter = plugboard[letter];

        letter = rightRotor.forward(letter);
        letter = midRotor.forward(letter);
        letter = leftRotor.forward(letter);
        letter = cipherForwardExtra(letter);

        letter = reflector[letter];

        letter = cipherBackwardExtra(letter);
        letter = leftRotor.backward(letter);
        letter = midRotor.backward(letter);
        letter = rightRotor.backward(letter);

        return (null != plugboard) ? plugboard[letter] : letter;
    }

    public final int[] cipher(int[] text) {
        final int[] retval = new int[text.length];
        for (int i = 0; i < retval.length; i++) {
            rotate();
            retval[i] = cipher(text[i]);
        }
        return retval;
    }
}
