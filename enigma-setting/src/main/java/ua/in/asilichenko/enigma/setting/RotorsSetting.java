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

package ua.in.asilichenko.enigma.setting;

import java.util.Arrays;
import java.util.List;

/**
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
