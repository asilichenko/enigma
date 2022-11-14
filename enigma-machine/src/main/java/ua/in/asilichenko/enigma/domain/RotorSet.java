package ua.in.asilichenko.enigma.domain;

import java.util.List;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 05.10.2022
 */
public class RotorSet {

    private final Reflector reflector;
    private final List<Rotor> rotors;

    private boolean incrementDisabled = false;

    public RotorSet(Reflector reflector, List<Rotor> rotors) {
        this.reflector = reflector;
        this.rotors = rotors;
    }

    public List<Rotor> getRotors() {
        return rotors;
    }

    public void disableIncrement() {
        incrementDisabled = true;
    }

    @SuppressWarnings("unused")
    public void enableIncrement() {
        incrementDisabled = false;
    }

    public boolean isIncrementDisabled() {
        return incrementDisabled;
    }

    public int reflect(int letter) {
        return reflector.reflect(letter);
    }
}
