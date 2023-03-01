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

import ua.in.asilichenko.enigma.setting.EnigmaSetting;

import java.util.*;

import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * Service to parse setting string and map it to object of {@link EnigmaSetting} class.
 * <p>
 * Creation date: 08.11.2022
 */
public class EnigmaSettingService {

    private static final int MIN_ROTORS_NUMBER = 3;

    static final String REFLECTOR_VALIDATION_MSG = "Reflector must be defined";
    static final String ROTORS_VALIDATION_MSG = "Rotors must be defined";
    static final String ROTORS_NUMBER_VALIDATION_MSG = "Rotors minimum number is " + MIN_ROTORS_NUMBER;
    static final String RINGS_NUMBER_VALIDATION_MSG = "Rings number must be equal to rotors number";
    static final String INDICATORS_NUMBER_VALIDATION_MSG = "Indicators number must be equal to rotors number";
    static final String PLUGS_LEN_VALIDATION_MSG = "Plug pairs must contain 2 letters";
    static final String PLUGS_UNIQUE_VALIDATION_MSG = "Plugs must be unique, but: ";

    final int[] parseIntArray(String setting, int rotorsNumber, String errMsg) {
        final int[] retval;// = new int[rotorsNumber];

        if (setting.contains(" ")) {
            final String[] numbers = setting.split(" ");
            validateSize(numbers.length, rotorsNumber, errMsg);

            retval = new int[rotorsNumber];
            for (int i = 0; i < retval.length; i++) retval[i] = parseInt(numbers[i], errMsg);

//            for (String number : numbers) retval.add(parseInt(number, errMsg));
        } else {
            validateSize(setting.length(), rotorsNumber, errMsg);
//            for (int e : indexOf(setting)) retval.add(e);
            retval = indexOf(setting);
        }

        return retval;
    }

    private int parseInt(String val, String errMsg) {
        try {
            return Integer.valueOf(val);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(errMsg, nfe);
        }
    }

    /**
     * Valid formats:
     * <pre>
     * "UKW-B, I II III, AAA, BBB, QW ER TY UI OP AS DF GH JK ZX"
     * "UKW-B, I II III, AAA, 3 4 5, QW ER TY UI OP AS DF GH JK ZX"
     * "UKW-B, I II III, 0 1 2, BBB, QW ER TY UI OP AS DF GH JK ZX"
     * "UKW-B, I II III, 0 1 2, 3 4 5, QW ER TY UI OP AS DF GH JK ZX"
     * </pre>
     *
     * @param settingString reflector, rotors, rings, indicators, plug pairs
     * @return EnigmaSetting
     */
    public EnigmaSetting parse(String settingString) {
        validateReflector(settingString);

        final String[] setting = settingString.split(", ");
        final ListIterator<String> iterator = Arrays.asList(setting).listIterator();

        final String reflector = iterator.next();

        validateRotors(iterator);

        final List<String> rotors = Arrays.asList(iterator.next().split(" "));
        final int rotorsNumber = rotors.size();

        validateRotorsNumber(rotorsNumber);

        int[] rings = new int[rotorsNumber];
        int[] indicators = new int[rotorsNumber];
//        List<Integer> rings = nCopies(rotorsNumber, 0);
//        List<Integer> indicators = nCopies(rotorsNumber, 0);
        String[] plugPairs = new String[0];

        if (iterator.hasNext()) {
            final String ringSection = iterator.next();
            rings = parseIntArray(ringSection, rotorsNumber, RINGS_NUMBER_VALIDATION_MSG);

            if (iterator.hasNext()) {
                final String indicatorSection = iterator.next();
                indicators = parseIntArray(indicatorSection, rotorsNumber, INDICATORS_NUMBER_VALIDATION_MSG);

                if (iterator.hasNext()) {
                    final String plugboardSetting = iterator.next();
                    plugPairs = plugboardSetting.split(" ");
                    validatePlugPairs(plugPairs);
                }
            }
        }

        return new EnigmaSetting(reflector, rotors, rings, indicators, plugPairs);
    }

    private void validatePlugPairs(String[] plugPairs) {
        final Set<Character> plugs = new HashSet<>();
        for (String plugPair : plugPairs) {
            if (2 != plugPair.length()) {
                throw new IllegalArgumentException(PLUGS_LEN_VALIDATION_MSG);
            }
            if (!plugs.add(plugPair.charAt(0)) || !plugs.add(plugPair.charAt(1))) {
                throw new IllegalArgumentException(PLUGS_UNIQUE_VALIDATION_MSG + plugPair);
            }
        }
    }

    private void validateRotorsNumber(int rotorsNumber) {
        if (rotorsNumber < MIN_ROTORS_NUMBER) {
            throw new IllegalArgumentException(ROTORS_NUMBER_VALIDATION_MSG);
        }
    }

    private void validateReflector(String settingString) {
        if (null == settingString || settingString.isEmpty()) {
            throw new IllegalArgumentException(REFLECTOR_VALIDATION_MSG);
        }
    }

    private void validateRotors(ListIterator<String> iterator) {
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException(ROTORS_VALIDATION_MSG);
        }
    }

    private void validateSize(int actual, int expected, String errMsg) {
        if (actual != expected) {
            throw new IllegalArgumentException(errMsg);
        }
    }
}
