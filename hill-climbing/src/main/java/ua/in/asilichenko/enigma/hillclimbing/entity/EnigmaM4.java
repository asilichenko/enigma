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
 * Creation date: 11.11.2022
 */
public class EnigmaM4 extends AbstractEnigma {

    private final Rotor extraRotor;

    /**
     * For build.
     */
    public EnigmaM4(int[] reflector, Rotor extraRotor, Rotor leftRotor, Rotor midRotor, Rotor rightRotor) {
        this(reflector, extraRotor, leftRotor, midRotor, rightRotor, null);
    }

    /**
     * For decipher.
     */
    public EnigmaM4(int[] reflector, Rotor extraRotor, Rotor leftRotor, Rotor midRotor, Rotor rightRotor, int[] plugboard) {
        super(reflector, leftRotor, midRotor, rightRotor, plugboard);
        this.extraRotor = extraRotor;
    }

    @Override
    public void adjustIndicators(int[] indicators) {
        extraRotor.setIndicator(indicators[0]);
        leftRotor.setIndicator(indicators[1]);
        midRotor.setIndicator(indicators[2]);
        rightRotor.setIndicator(indicators[3]);
    }

    @Override
    int cipherForwardExtra(int letter) {
        return extraRotor.forward(letter);
    }

    @Override
    int cipherBackwardExtra(int letter) {
        return extraRotor.backward(letter);
    }
}
