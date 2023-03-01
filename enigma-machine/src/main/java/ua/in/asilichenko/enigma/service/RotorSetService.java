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

package ua.in.asilichenko.enigma.service;

import ua.in.asilichenko.enigma.domain.Reflector;
import ua.in.asilichenko.enigma.domain.Rotor;
import ua.in.asilichenko.enigma.domain.RotorSet;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;

/**
 * Creation date: 11.10.2022
 */
public class RotorSetService {

    private RotorService rotorService;
    private ReflectorService reflectorService;

    public void setRotorService(RotorService rotorService) {
        this.rotorService = rotorService;
    }

    public void setReflectorService(ReflectorService reflectorService) {
        this.reflectorService = reflectorService;
    }

    public RotorSet build(String reflectorName, List<String> rotorNames, int[] rings, int[] indicators) {
        final Reflector reflector = reflectorService.reflectorByName(reflectorName);
        if (null == reflector) {
            throw new IllegalStateException("Reflector not found: " + reflectorName);
        }

        final List<Rotor> rotors = new ArrayList<>();
        for (int i = 0; i < rotorNames.size(); i++) {
            final String rotorName = rotorNames.get(i);
            final Rotor rotor = rotorService.rotorByName(rotorName);
            if (null == rotor) {
                throw new IllegalStateException("Rotor not found: " + rotorName);
            }
            rotor.setRing(rings[i]);
            rotor.setIndicator(indicators[i]);
            rotors.add(rotor);
        }

        return new RotorSet(reflector, rotors);
    }

    private void validateSize(List<Rotor> rotors, int[] ringSetting) {
        if (rotors.size() != ringSetting.length) {
            throw new IllegalArgumentException(String.format("Incompatible rotors size: %d and ring setting size: %d.", rotors.size(), ringSetting.length));
        }
    }

    private void validateRingPos(int rotorNumber, int ringPos) {
        if (ringPos < 0 || ringPos >= LETTERS_COUNT) {
            throw new IllegalArgumentException(String.format("Incorrect ring %s setting: %s. Must be between 0 and %d.", rotorNumber, ringPos, LETTERS_COUNT - 1));
        }
    }

    private void validateIndicator(int rotorNumber, int indicator) {
        if (indicator < 0 || indicator >= LETTERS_COUNT) {
            throw new IllegalArgumentException(String.format("Incorrect indicator %s setting: %s. Must be between 0 and %d.", rotorNumber, indicator, LETTERS_COUNT - 1));
        }
    }

    public void adjustRings(RotorSet rotorSet, int[] ringSetting) {
        final List<Rotor> rotors = rotorSet.getRotors();
        validateSize(rotors, ringSetting);
        for (int i = 0; i < rotors.size(); i++) {
            final int ringPos = ringSetting[i];
            validateRingPos(i, ringPos);
            rotors.get(i).setRing(ringPos);
        }
    }

    public void adjustIndicators(RotorSet rotorSet, int[] indicatorSetting) {
        final List<Rotor> rotors = rotorSet.getRotors();
        validateSize(rotors, indicatorSetting);
        for (int i = 0; i < rotors.size(); i++) {
            final int indicator = indicatorSetting[i];
            validateIndicator(i, indicator);
            rotors.get(i).setIndicator(indicator);
        }
    }

    /**
     * The right rotor is incremented always.
     * <p>
     * The middle - only when itself or the right rotor is at notch position.
     * <p>
     * The left - only when the middle is at notch position.
     * <p>
     * There is double stepping anomaly:
     * rotor at the notch position is incremented by moving at the notch on it.
     * If notch of the rotor is present on current rotor position then this rotor ticks again.
     *
     * @see <a href="https://www.cryptomuseum.com/crypto/enigma/working.htm#stepping">Rotors stepping</a>.
     */
    protected void increment(RotorSet rotorSet) {
        if (rotorSet.isIncrementDisabled()) return;

        final List<Rotor> rotors = rotorSet.getRotors();
        final ListIterator<Rotor> iterator = rotors.listIterator(rotors.size());
        final Rotor last = iterator.previous();
        final Rotor prev = iterator.previous();
        final Rotor prePrev = iterator.previous();

        if (null != prePrev && rotorService.isInNotchPosition(prev)) {
            rotorService.incrementIndicator(prev);
            rotorService.incrementIndicator(prePrev);
        } else if (null != prev && rotorService.isInNotchPosition(last)) {
            rotorService.incrementIndicator(prev);
        }
        rotorService.incrementIndicator(last);
    }

    /**
     * Cipher index of letter by passing through set of rotors and reflecting at reflector.
     *
     * @param rotorSet RotorSet of rotors and reflector
     * @param letter   letter index
     * @return index of enciphered letter
     */
    public int cipher(RotorSet rotorSet, int letter) {
        int retval = letter;
        final List<Rotor> rotors = rotorSet.getRotors();
        final ListIterator<Rotor> iterator = rotors.listIterator(rotors.size());

        while (iterator.hasPrevious()) retval = rotorService.cipherForward(iterator.previous(), retval);
        retval = rotorSet.reflect(retval);
        while (iterator.hasNext()) retval = rotorService.cipherBackward(iterator.next(), retval);

        return retval;
    }
}
