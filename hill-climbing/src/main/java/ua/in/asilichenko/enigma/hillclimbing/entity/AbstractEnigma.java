package ua.in.asilichenko.enigma.hillclimbing.entity;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 12.11.2022
 */
public abstract class AbstractEnigma {

    private final int[] reflector;

    final Rotor leftRotor;
    final Rotor midRotor;
    final Rotor rightRotor;

    private final int[] plugboard;

    AbstractEnigma(int[] reflector, Rotor leftRotor, Rotor midRotor, Rotor rightRotor, int[] plugboard) {
        this.reflector = reflector;
        this.leftRotor = leftRotor;
        this.midRotor = midRotor;
        this.rightRotor = rightRotor;
        this.plugboard = plugboard;
    }

    public abstract void adjustIndicators(int[] indicators);

    public final void rotate() {
        if (midRotor.isInNotchPos()) {
            midRotor.rotate();
            leftRotor.rotate();
        } else if (rightRotor.isInNotchPos()) {
            midRotor.rotate();
        }
        rightRotor.rotate();
    }

    int cipherForwardExtra(int letter) {
        return letter;
    }

    int cipherBackwardExtra(int letter) {
        return letter;
    }

    public final int cipher(int letter) {
        if (null != plugboard) letter = plugboard[letter];

        letter = rightRotor.forward(letter);
        letter = midRotor.forward(letter);
        letter = leftRotor.forward(letter);
        letter = cipherForwardExtra(letter);

        letter = reflector[letter];

        letter = cipherBackwardExtra(letter);
        letter = leftRotor.backward(letter);
        letter = midRotor.backward(letter);
        letter = rightRotor.backward(letter);

        return (null != plugboard) ? plugboard[letter] : letter;
    }

    public final int[] cipher(int[] text) {
        final int[] retval = new int[text.length];
        for (int i = 0; i < retval.length; i++) {
            rotate();
            retval[i] = cipher(text[i]);
        }
        return retval;
    }
}
