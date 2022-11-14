package ua.in.asilichenko.enigma.hillclimbing.utils;

import ua.in.asilichenko.enigma.hillclimbing.result.SearchResult;
import ua.in.asilichenko.enigma.util.PlugboardUtils;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 12.11.2022
 */
public class SearchResultUtils {

    public static String asString(SearchResult result) {
        return result.getRotorsSetting() + " "
                + result.getRingsSetting() + " "
                + result.getIndicatorsSetting() + " "
                + PlugboardUtils.asString(result.getPlugboard());
    }
}
