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

import java.util.List;

/**
 * Setting for different parts of Enigma machine.
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
