package ua.in.asilichenko.enigma.service;

import ua.in.asilichenko.enigma.dao.WheelDao;
import ua.in.asilichenko.enigma.domain.Rotor;
import ua.in.asilichenko.enigma.domain.RotorSet;
import ua.in.asilichenko.enigma.entity.Wheel;
import ua.in.asilichenko.enigma.util.RotorUtils;

import java.util.List;

import static ua.in.asilichenko.enigma.domain.Rotor.DISPLAY_TO_NOTCH_SHIFT;
import static ua.in.asilichenko.enigma.util.LetterUtils.*;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 05.10.2022
 */
public class RotorService {

    private static final String VALIDATION_WIRING_MSG = "Incorrect wheel wiring: %s. Length must be: %d, but was: %d";

    private WheelDao wheelDao;

    public void setWheelDao(WheelDao wheelDao) {
        this.wheelDao = wheelDao;
    }

    public Rotor rotorByName(String name) {
        final Wheel wheel = wheelDao.forName(name);

        if (null == wheel) return null;

        final String wiring = wheel.getWiring();
        validateWiring(wiring);
        final int[] forwardWiring = forwardWiringOf(wiring);
        final int[] backwardWiring = backwardWiringOf(forwardWiring);

        return new Rotor(name, forwardWiring, backwardWiring, wheel.getNotches());
    }

    private void validateWiring(String wiring) {
        if (LETTERS_COUNT != wiring.length()) {
            throw new IllegalStateException(String.format(VALIDATION_WIRING_MSG, wiring, LETTERS_COUNT, wiring.length()));
        }
    }

    protected int[] forwardWiringOf(String wiring) {
        return indexOf(wiring);
    }

    protected int[] backwardWiringOf(int[] wiring) {
        return RotorUtils.reverse(wiring);
    }

    protected void incrementIndicator(Rotor rotor) {
        rotor.setIndicator(mod(rotor.getIndicator() + 1));
    }

    /**
     * Check if rotor's notch is at current rotor position.
     *
     * @param rotor Rotor wheel
     * @return boolean
     */
    boolean isInNotchPosition(Rotor rotor) {
        final int[] notches = rotor.getNotches();
        for (int notch : notches) {
            if (notch == mod(rotor.getIndicator() + DISPLAY_TO_NOTCH_SHIFT)) {
                return true;
            }
        }
        return false;
    }

    private int obtainShift(Rotor rotor) {
        return mod(rotor.getIndicator() - rotor.getRing());
    }

    public int cipher(int[] wiring, int shift, int letter) {
        final int shiftedLetter = mod(letter + shift);
        final int enciphered = wiring[shiftedLetter];
        return mod(enciphered - shift);
    }

    public int cipherForward(Rotor rotor, int letter) {
        final int shift = obtainShift(rotor);
        return cipher(rotor.getForwardWiring(), shift, letter);
    }

    public int cipherBackward(Rotor rotor, int letter) {
        final int shift = obtainShift(rotor);
        return cipher(rotor.getBackwardWiring(), shift, letter);
    }

    public void adjustIndicators(RotorSet rotorSet, String indicators) {
        final List<Rotor> rotors = rotorSet.getRotors();
        for (int i = 0; i < indicators.length(); i++) {
            final int indicator = indexOf(indicators.charAt(i));
            rotors.get(i).setIndicator(indicator);
        }
    }
}
