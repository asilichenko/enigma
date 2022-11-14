package ua.in.asilichenko.enigma.entity;

/**
 * Wheel entity.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 12.10.2022
 */
public class Wheel {

    private String wiring;
    private int[] notches = new int[0];

    public int[] getNotches() {
        return notches;
    }

    @SuppressWarnings("unused") // is used by JSON mapper
    public void setNotches(int[] notches) {
        this.notches = notches;
    }

    public String getWiring() {
        return wiring;
    }

    @SuppressWarnings("unused") // is used by JSON mapper
    public void setWiring(String wiring) {
        this.wiring = wiring;
    }
}
