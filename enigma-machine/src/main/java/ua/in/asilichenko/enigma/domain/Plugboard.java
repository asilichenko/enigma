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

import java.util.Arrays;

import static ua.in.asilichenko.enigma.util.LetterUtils.ABC;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * Creation date: 06.10.2022
 */
public class Plugboard {

    private static final int[] DEFAULT = indexOf(ABC);

    private final int[] layout;

    public Plugboard() {
        this(Arrays.copyOf(DEFAULT, DEFAULT.length));
    }

    @SuppressWarnings("WeakerAccess")
    public Plugboard(int[] layout) {
        this.layout = layout;
    }

    public int[] getLayout() {
        return layout;
    }
}
