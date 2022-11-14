package ua.in.asilichenko.enigma.hillclimbing.result;

import ua.in.asilichenko.enigma.setting.RotorsSetting;
import ua.in.asilichenko.enigma.setting.IndicatorsSetting;
import ua.in.asilichenko.enigma.setting.RingsSetting;

import javax.annotation.Nonnull;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
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