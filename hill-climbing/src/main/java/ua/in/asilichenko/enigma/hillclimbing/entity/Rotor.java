package ua.in.asilichenko.enigma.hillclimbing.entity;

import static ua.in.asilichenko.enigma.util.RotorUtils.reverse;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.mod;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
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
