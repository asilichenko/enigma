package ua.in.asilichenko.enigma.setting;

import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
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
