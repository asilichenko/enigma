package ua.in.asilichenko.enigma.hillclimbing.entity;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 11.11.2022
 */
public class EnigmaM4 extends AbstractEnigma {

    private final Rotor extraRotor;

    /**
     * For build.
     */
    public EnigmaM4(int[] reflector, Rotor extraRotor, Rotor leftRotor, Rotor midRotor, Rotor rightRotor) {
        this(reflector, extraRotor, leftRotor, midRotor, rightRotor, null);
    }

    /**
     * For decipher.
     */
    public EnigmaM4(int[] reflector, Rotor extraRotor, Rotor leftRotor, Rotor midRotor, Rotor rightRotor, int[] plugboard) {
        super(reflector, leftRotor, midRotor, rightRotor, plugboard);
        this.extraRotor = extraRotor;
    }

    @Override
    public void adjustIndicators(int[] indicators) {
        extraRotor.setIndicator(indicators[0]);
        leftRotor.setIndicator(indicators[1]);
        midRotor.setIndicator(indicators[2]);
        rightRotor.setIndicator(indicators[3]);
    }

    @Override
    int cipherForwardExtra(int letter) {
        return extraRotor.forward(letter);
    }

    @Override
    int cipherBackwardExtra(int letter) {
        return extraRotor.backward(letter);
    }
}
