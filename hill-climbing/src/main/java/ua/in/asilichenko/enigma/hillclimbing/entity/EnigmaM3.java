package ua.in.asilichenko.enigma.hillclimbing.entity;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 04.11.2022
 */
public class EnigmaM3 extends AbstractEnigma {

    /**
     * For build.
     */
    public EnigmaM3(int[] reflector, Rotor leftRotor, Rotor midRotor, Rotor rightRotor) {
        this(reflector, leftRotor, midRotor, rightRotor, null); // null-plugboard is faster than empty
    }

    /**
     * For decipher.
     */
    public EnigmaM3(int[] reflector, Rotor leftRotor, Rotor midRotor, Rotor rightRotor, int[] plugboard) {
        super(reflector, leftRotor, midRotor, rightRotor, plugboard);
    }

    @Override
    public void adjustIndicators(int[] indicators) {
        leftRotor.setIndicator(indicators[0]);
        midRotor.setIndicator(indicators[1]);
        rightRotor.setIndicator(indicators[2]);
    }
}
