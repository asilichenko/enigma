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

package ua.in.asilichenko.enigma.entity;

/**
 * Wheel entity.
 * <p>
 * Creation date: 12.10.2022
 */
public class Wheel {

    private String wiring;
    private int[] notches = new int[0];

    public int[] getNotches() {
        return notches;
    }

    @SuppressWarnings("unused") // is used by JSON mapper
    public void setNotches(int[] notches) {
        this.notches = notches;
    }

    public String getWiring() {
        return wiring;
    }

    @SuppressWarnings("unused") // is used by JSON mapper
    public void setWiring(String wiring) {
        this.wiring = wiring;
    }
}
