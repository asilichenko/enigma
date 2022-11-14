package ua.in.asilichenko.enigma.setting;

/**
 * Ring setting for the middle and right rotors.
 * Position of the left rotor ring doesn't matter.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 27.10.2022
 */
public class RingsSetting {

    private final int ringMid;
    private final int ringRight;

    public RingsSetting(int ringMid, int ringRight) {
        this.ringMid = ringMid;
        this.ringRight = ringRight;
    }

    @SuppressWarnings("unused")
    public int getRingLeft() {
        return 0;
    }

    public int getRingMid() {
        return ringMid;
    }

    public int getRingRight() {
        return ringRight;
    }

    @Override
    public String toString() {
        return ringMid + " " + ringRight;
    }
}
