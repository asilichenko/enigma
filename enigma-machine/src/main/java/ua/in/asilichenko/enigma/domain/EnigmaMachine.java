package ua.in.asilichenko.enigma.domain;

import javax.annotation.Nonnull;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 11.10.2022
 */
public class EnigmaMachine {

    private final RotorSet rotorSet;
    private final Plugboard plugboard;

    public EnigmaMachine(@Nonnull RotorSet rotorSet, @Nonnull Plugboard plugboard) {
        this.rotorSet = rotorSet;
        this.plugboard = plugboard;
    }

    public RotorSet getRotorSet() {
        return rotorSet;
    }

    public Plugboard getPlugboard() {
        return plugboard;
    }
}
