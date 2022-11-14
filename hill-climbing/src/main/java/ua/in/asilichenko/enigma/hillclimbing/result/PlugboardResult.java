package ua.in.asilichenko.enigma.hillclimbing.result;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 27.10.2022
 */
public class PlugboardResult {

    private final int[] plugboard;
    private final long score;

    public PlugboardResult(int[] plugboard, long score) {
        this.plugboard = plugboard;
        this.score = score;
    }

    public int[] getPlugboard() {
        return plugboard;
    }

    public long getScore() {
        return score;
    }
}
