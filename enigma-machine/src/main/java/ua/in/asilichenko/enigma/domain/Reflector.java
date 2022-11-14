package ua.in.asilichenko.enigma.domain;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 28.10.2022
 */
public class Reflector {

    private final String name;
    private final int[] wiring;

    public Reflector(String name, int[] wiring) {
        this.name = name;
        this.wiring = wiring;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public int reflect(int letter) {
        return wiring[letter];
    }
}
