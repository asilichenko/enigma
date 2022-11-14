package ua.in.asilichenko.enigma.setting;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 12.11.2022
 */
public class RotorsSetting {

    private final String reflector;
    private final List<String> rotors;

    private RotorsSetting(String reflector, List<String> rotors) {
        this.reflector = reflector;
        this.rotors = rotors;
    }

    public RotorsSetting(String reflector, String rotorLeft, String rotorMid, String rotorRight) {
        this(reflector, Arrays.asList(rotorLeft, rotorMid, rotorRight));
    }

    public String getReflector() {
        return reflector;
    }

    public List<String> getRotors() {
        return rotors;
    }

    @Override
    public String toString() {
        return reflector + " " + rotors.toString();
    }
}
