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

import javax.annotation.Nonnull;

/**
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
