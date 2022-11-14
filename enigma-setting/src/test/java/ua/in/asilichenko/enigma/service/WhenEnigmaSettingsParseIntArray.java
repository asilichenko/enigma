package ua.in.asilichenko.enigma.service;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.11.2022
 */
public class WhenEnigmaSettingsParseIntArray {
    // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
    // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z

    private static final int ROTORS_NUMBER = 3;

    private static final String VALIDATION_MSG = "";

    private final EnigmaSettingService enigmaSettingService = new EnigmaSettingService();

    @Test
    public void arrayOfStringNumbersShouldBeArrayOfNumbers() {
        final int[] actual = enigmaSettingService.parseIntArray("10 11 22", ROTORS_NUMBER, VALIDATION_MSG);
        assertThat(actual, is(Arrays.asList(10, 11, 22)));
    }

    @Test
    public void stringLettersShouldBeArrayOfLetterIndexes() {
        final int[] actual = enigmaSettingService.parseIntArray("PFT", ROTORS_NUMBER, VALIDATION_MSG);
        assertThat(actual, is(Arrays.asList(15, 5, 19)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void elementsNumberLetterFormatLtRotorsNumberShouldBeException() {
        enigmaSettingService.parseIntArray("AB", ROTORS_NUMBER, VALIDATION_MSG);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void elementsNumberIntFormatLtRotorsNumberShouldBeException() {
        enigmaSettingService.parseIntArray("10 20", ROTORS_NUMBER, VALIDATION_MSG);
    }
}
