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

/**
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
