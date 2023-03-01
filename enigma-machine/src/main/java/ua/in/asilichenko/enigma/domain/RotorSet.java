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

import java.util.List;

/**
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
