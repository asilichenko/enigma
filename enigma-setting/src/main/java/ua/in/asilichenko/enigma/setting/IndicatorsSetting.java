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

import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * Creation date: 12.11.2022
 */
public class IndicatorsSetting {

    private final int[] indicators;

    private IndicatorsSetting(int[] indicators) {
        this.indicators = indicators;
    }

    public IndicatorsSetting(int left, int mid, int right) {
        this(new int[]{left, mid, right});
    }

    public IndicatorsSetting(int extra, int left, int mid, int right) {
        this(new int[]{extra, left, mid, right});
    }

    public int[] getIndicators() {
        return indicators;
    }

    @Override
    public String toString() {
        return stringOf(indicators);
    }
}
