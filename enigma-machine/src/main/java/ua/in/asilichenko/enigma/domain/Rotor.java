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

package ua.in.asilichenko.enigma.domain;

/**
 * Enigma machine rotor.
 * <p>
 * More about how rotor works:
 * <a href="https://hackaday.com/2017/08/22/the-enigma-enigma-how-the-enigma-machine-worked/">How Enigma machine worked</a>.
 * <p>
 * Wiring of the rotors could be found here:
 * <a href="https://www.cryptomuseum.com/crypto/enigma/wiring.htm">Wiring</a>.
 * <p>
 * Creation date: 05.10.2022
 */
public class Rotor {

    /**
     * How far is the notch sensing position from the visible position
     */
    public static final int DISPLAY_TO_NOTCH_SHIFT = 8;

    private final String name;

    /**
     * Position of the ring relatively to the rotor input layout:
     * <ul>
     * <li>0 means: 01 eq. 'A'</li>
     * <li>1 means: 02 eq. 'B'</li>
     * ...
     * <li>25 means: 26 eq. 'Z'</li>
     * </ul>
     */
    private int ring = 0;

    /**
     * Position of the notch on the ring relatively to mark 01 ('A')
     */
    private final int[] notches;

    /**
     * Index of the visible mark on the ring (visible mark 01 = 0 indicator, etc.)
     */
    private int indicator = 0;

    /**
     * Ciphered letters relatively to input layout (alphabet).
     * <p>
     * For example:
     * <pre>
     * forwardWiring[0] == 4 means alphabet letter 'A'(0) is ciphered with letter 'E'(4)
     * </pre>
     */
    private final int[] forwardWiring;
    /**
     * Ciphered alphabet from the backward view.
     * <p>
     * If letter 'A' is enciphered as 'E' from forward view, then letter 'E' is enciphered as 'A' from backward view.
     */
    private final int[] backwardWiring;

    public Rotor(String name, int[] forwardWiring, int[] backwardWiring, int notch) {
        this(name, forwardWiring, backwardWiring, new int[]{notch});
    }

    public Rotor(String name, int[] forwardWiring, int[] backwardWiring, int[] notches) {
        this.name = name;
        this.forwardWiring = forwardWiring;
        this.backwardWiring = backwardWiring;
        this.notches = notches;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public int[] getNotches() {
        return notches;
    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public int[] getForwardWiring() {
        return forwardWiring;
    }

    public int[] getBackwardWiring() {
        return backwardWiring;
    }

    /**
     * Is used when test failed.
     *
     * @return rotor name
     */
    @Override
    public String toString() {
        return String.format("Rotor_%s", name);
    }
}
