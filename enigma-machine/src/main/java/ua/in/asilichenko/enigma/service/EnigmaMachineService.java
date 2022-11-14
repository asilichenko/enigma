package ua.in.asilichenko.enigma.service;

import ua.in.asilichenko.enigma.domain.EnigmaMachine;
import ua.in.asilichenko.enigma.domain.Plugboard;
import ua.in.asilichenko.enigma.domain.RotorSet;
import ua.in.asilichenko.enigma.setting.EnigmaSetting;

import java.nio.CharBuffer;
import java.util.List;

import static ua.in.asilichenko.enigma.util.LetterUtils.*;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 07.10.2022
 */
public class EnigmaMachineService {

    private static final String LETTER_VALIDATION_MSG = "Incorrect letter: %d. Expected in range: [0, %d].";

    private RotorService rotorService;
    private RotorSetService rotorSetService;
    private PlugboardService plugboardService;
    private EnigmaSettingService enigmaSettingService;

    public void setEnigmaSettingService(EnigmaSettingService enigmaSettingService) {
        this.enigmaSettingService = enigmaSettingService;
    }

    public void setRotorService(RotorService rotorService) {
        this.rotorService = rotorService;
    }

    public void setPlugboardService(PlugboardService plugboardService) {
        this.plugboardService = plugboardService;
    }

    public void setRotorSetService(RotorSetService rotorSetService) {
        this.rotorSetService = rotorSetService;
    }

    @SuppressWarnings("WeakerAccess")
    public EnigmaMachine build(
            String reflectorName,
            List<String> rotorsNames,
            int[] rings,
            int[] indicators,
            String[] plugPairs
    ) {
        final RotorSet rotorSet = rotorSetService.build(reflectorName, rotorsNames, rings, indicators);
        final Plugboard plugboard = plugboardService.createPlugboard(plugPairs);
        return new EnigmaMachine(rotorSet, plugboard);
    }

    public EnigmaMachine build(String settingString) {
        final EnigmaSetting setting = enigmaSettingService.parse(settingString);
        return build(
                setting.getReflector(),
                setting.getRotors(),
                setting.getRings(),
                setting.getIndicators(),
                setting.getPlugPairs()
        );
    }

    /**
     * Just pass through plugboard-rotors-plugboard.
     *
     * @param enigmaMachine enigma machine object
     * @param letter        letter to pass
     * @return scrambled letter
     */
    @SuppressWarnings("WeakerAccess")
    public int pass(EnigmaMachine enigmaMachine, int letter) {
        int retval = letter;
        retval = plugboardService.cipher(enigmaMachine.getPlugboard(), retval);
        retval = rotorSetService.cipher(enigmaMachine.getRotorSet(), retval);
        retval = plugboardService.cipher(enigmaMachine.getPlugboard(), retval);
        return retval;
    }

    /**
     * Validate letter, increment rotor position and pass an input letter.
     *
     * @param enigmaMachine enigma machine instance
     * @param letter        letter to encipher
     * @return ciphered letter
     */
    int cipher(EnigmaMachine enigmaMachine, int letter) {
        validateLetter(letter);
        increment(enigmaMachine);
        return pass(enigmaMachine, letter);
    }

    public String cipher(EnigmaMachine enigmaMachine, String text) {
        final CharBuffer retval = CharBuffer.allocate(text.length());
        for (int i = 0; i < text.length(); i++) {
            final int letter = indexOf(text.charAt(i));
            final int ciphered = cipher(enigmaMachine, letter);
            retval.append(letterOf(ciphered));
        }
        return retval.rewind().toString();
    }

    @SuppressWarnings("unused")
    public String cipher(EnigmaMachine enigmaMachine, String indicatorsSetting, String text) {
        adjustIndicators(enigmaMachine, indicatorsSetting);
        return cipher(enigmaMachine, text);
    }

    private void validateLetter(int letter) {
        if (letter < 0 || letter >= LETTERS_COUNT) {
            throw new IllegalArgumentException(String.format(LETTER_VALIDATION_MSG, letter, LETTERS_COUNT - 1));
        }
    }

    public void disableRotorsIncrement(EnigmaMachine enigmaMachine) {
        enigmaMachine.getRotorSet().disableIncrement();
    }

    @SuppressWarnings("WeakerAccess")
    public void increment(EnigmaMachine enigmaMachine) {
        rotorSetService.increment(enigmaMachine.getRotorSet());
    }

    @SuppressWarnings("unused")
    public void adjustRings(EnigmaMachine enigma, int[] rings) {
        rotorSetService.adjustRings(enigma.getRotorSet(), rings);
    }

    @SuppressWarnings("unused")
    public void adjustIndicators(EnigmaMachine enigma, int[] indicators) {
        rotorSetService.adjustIndicators(enigma.getRotorSet(), indicators);
    }

    @SuppressWarnings("WeakerAccess")
    public void adjustIndicators(EnigmaMachine enigmaMachine, String indicators) {
        rotorService.adjustIndicators(enigmaMachine.getRotorSet(), indicators);
    }

    @SuppressWarnings("unused")
    public void connectPlugPair(EnigmaMachine enigmaMachine, String plugPair) {
        plugboardService.connectPlugPair(enigmaMachine.getPlugboard(), plugPair);
    }

    @SuppressWarnings("unused")
    public void adjustPlugboard(EnigmaMachine enigma, String[] plugPairs) {
        plugboardService.adjustPlugboard(enigma.getPlugboard(), plugPairs);
    }
}
