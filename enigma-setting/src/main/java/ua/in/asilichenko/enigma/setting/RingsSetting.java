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

/**
 * Ring setting for the middle and right rotors.
 * Position of the left rotor ring doesn't matter.
 * <p>
 * Creation date: 27.10.2022
 */
public class RingsSetting {

    private final int ringMid;
    private final int ringRight;

    public RingsSetting(int ringMid, int ringRight) {
        this.ringMid = ringMid;
        this.ringRight = ringRight;
    }

    @SuppressWarnings("unused")
    public int getRingLeft() {
        return 0;
    }

    public int getRingMid() {
        return ringMid;
    }

    public int getRingRight() {
        return ringRight;
    }

    @Override
    public String toString() {
        return ringMid + " " + ringRight;
    }
}
