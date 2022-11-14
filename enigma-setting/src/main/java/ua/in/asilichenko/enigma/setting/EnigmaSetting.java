package ua.in.asilichenko.enigma.setting;

import java.util.List;

/**
 * Setting for different parts of Enigma machine.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 29.10.2022
 */
public class EnigmaSetting {

    private final String reflector;
    private final List<String> rotors;

    private final int[] rings;
    private final int[] indicators;
    private final String[] plugPairs;

    public EnigmaSetting(String reflector, List<String> rotors, int[] rings, int[] indicators, String[] plugPairs) {
        this.reflector = reflector;
        this.rotors = rotors;
        this.indicators = indicators;
        this.rings = rings;
        this.plugPairs = plugPairs;
    }

    public String getReflector() {
        return reflector;
    }

    public List<String> getRotors() {
        return rotors;
    }

    public int[] getIndicators() {
        return indicators;
    }

    public int[] getRings() {
        return rings;
    }

    public String[] getPlugPairs() {
        return plugPairs;
    }
}
