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
 * Creation date: 04.11.2022
 */
public class EnigmaM3 extends AbstractEnigma {

    /**
     * For build.
     */
    public EnigmaM3(int[] reflector, Rotor leftRotor, Rotor midRotor, Rotor rightRotor) {
        this(reflector, leftRotor, midRotor, rightRotor, null); // null-plugboard is faster than empty
    }

    /**
     * For decipher.
     */
    public EnigmaM3(int[] reflector, Rotor leftRotor, Rotor midRotor, Rotor rightRotor, int[] plugboard) {
        super(reflector, leftRotor, midRotor, rightRotor, plugboard);
    }

    @Override
    public void adjustIndicators(int[] indicators) {
        leftRotor.setIndicator(indicators[0]);
        midRotor.setIndicator(indicators[1]);
        rightRotor.setIndicator(indicators[2]);
    }
}
