package ua.in.asilichenko.enigma.domain;

import java.util.Arrays;

import static ua.in.asilichenko.enigma.util.LetterUtils.ABC;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
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
