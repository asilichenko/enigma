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

import static ua.in.asilichenko.enigma.util.RotorUtils.reverse;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.mod;

/**
 * Creation date: 04.11.2022
 */
public class Rotor {

    @SuppressWarnings("WeakerAccess")
    public static final int DISPLAY_TO_NOTCH = 8;
    /**
     * Index from end.
     */
    public static final int MID = 2;
    /**
     * Index from end.
     */
    public static final int RIGHT = 1;

    private final int[] notches;
    private final int[] forward;
    private final int[] backward;

    private final int ring;
    private int indicator = 0;

    /**
     * Slow constructor
     *
     * @param wiring  wiring
     * @param notches notches
     */
    public Rotor(String wiring, int[] notches) {
        this(wiring, notches, 0, 0);
    }

    /**
     * Slow constructor with ring and indicator
     *
     * @param wiring    wiring
     * @param notches   notches
     * @param ring      ring position
     * @param indicator indicator
     */
    public Rotor(String wiring, int[] notches, int ring, int indicator) {
        this.forward = indexOf(wiring);
        this.backward = reverse(forward);
        this.notches = notches;
        this.ring = ring;
        this.indicator = indicator;
    }

    /**
     * Rapid copy constructor
     *
     * @param that sample rotor
     */
    public Rotor(Rotor that) {
        this(that, 0);
    }

    /**
     * Rapid copy constructor with ring position
     *
     * @param that sample rotor
     * @param ring ring position
     */
    public Rotor(Rotor that, int ring) {
        this.notches = that.notches;
        this.forward = that.forward;
        this.backward = that.backward;
        this.ring = ring;
    }

    @SuppressWarnings("unused")
    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    boolean isInNotchPos() {
        for (int notch : notches) {
            if (notch == mod(indicator + DISPLAY_TO_NOTCH)) {
                return true;
            }
        }
        return false;
    }

    public void rotate() {
        indicator = mod(indicator + 1);
    }

    private int getShift() {
        return indicator - ring;
    }

    private int encipher(int letter, int[] wiring) {
        final int shift = getShift();
        return mod(wiring[mod(letter + shift)] - shift);
    }

    int forward(int letter) {
        return encipher(letter, forward);
    }

    int backward(int letter) {
        return encipher(letter, backward);
    }

    @SuppressWarnings("unused")
    public int[] getNotches() {
        return notches;
    }
}
