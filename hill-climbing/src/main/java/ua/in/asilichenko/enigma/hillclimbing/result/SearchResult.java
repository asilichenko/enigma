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

package ua.in.asilichenko.enigma.hillclimbing.result;

import ua.in.asilichenko.enigma.setting.RotorsSetting;
import ua.in.asilichenko.enigma.setting.IndicatorsSetting;
import ua.in.asilichenko.enigma.setting.RingsSetting;

import javax.annotation.Nonnull;

/**
 * Creation date: 27.10.2022
 */
public class SearchResult implements Comparable<SearchResult> {

    private final RotorsSetting rotorsSetting;
    private final RingsSetting ringsSetting;
    private final IndicatorsSetting indicatorsSetting;
    private final int[] plugboard;
    private final long score;

    public SearchResult(RotorsSetting rotorsSetting,
                        RingsSetting ringsSetting,
                        IndicatorsSetting indicatorsSetting,
                        int[] plugboard,
                        long score) {
        this.rotorsSetting = rotorsSetting;
        this.ringsSetting = ringsSetting;
        this.indicatorsSetting = indicatorsSetting;
        this.plugboard = plugboard;
        this.score = score;
    }

    public RotorsSetting getRotorsSetting() {
        return rotorsSetting;
    }

    public RingsSetting getRingsSetting() {
        return ringsSetting;
    }

    public IndicatorsSetting getIndicatorsSetting() {
        return indicatorsSetting;
    }

    public int[] getPlugboard() {
        return plugboard;
    }

    public long getScore() {
        return score;
    }

    @Override
    public int compareTo(@Nonnull SearchResult other) {
        return Long.compare(other.score, this.score); // descending
    }
}