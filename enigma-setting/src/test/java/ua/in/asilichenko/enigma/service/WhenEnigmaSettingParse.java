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

import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.setting.EnigmaSetting;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.service.EnigmaSettingService.*;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 09.11.2022
 */
public class WhenEnigmaSettingParse {
    // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
    // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z

    private final EnigmaSettingService enigmaSettingService = new EnigmaSettingService();

    @Test
    public void reflectorAndRotorsShouldBeParsed() {
        final EnigmaSetting actual = enigmaSettingService.parse("UKW-B, I II III");
        assertThat(actual.getReflector(), is("UKW-B"));
        assertThat(actual.getRotors(), is(Arrays.asList("I", "II", "III")));
        assertThat(actual.getRings(), is(Arrays.asList(0, 0, 0)));
        assertThat(actual.getIndicators(), is(Arrays.asList(0, 0, 0)));
        assertThat(actual.getPlugPairs(), is(new String[0]));
    }

    @Test
    public void ringsLetterFormatShouldBeParsed() {
        final EnigmaSetting actual = enigmaSettingService.parse("UKW-B, I II III, ABC");
        assertThat(actual.getReflector(), is("UKW-B"));
        assertThat(actual.getRotors(), is(Arrays.asList("I", "II", "III")));
        assertThat(actual.getRings(), is(Arrays.asList(0, 1, 2)));
        assertThat(actual.getIndicators(), is(Arrays.asList(0, 0, 0)));
        assertThat(actual.getPlugPairs(), is(new String[0]));
    }

    @Test
    public void ringsNumberFormatShouldBeParsed() {
        final EnigmaSetting actual = enigmaSettingService.parse("UKW-B, I II III, 0 10 12");
        assertThat(actual.getReflector(), is("UKW-B"));
        assertThat(actual.getRotors(), is(Arrays.asList("I", "II", "III")));
        assertThat(actual.getRings(), is(Arrays.asList(0, 10, 12)));
        assertThat(actual.getIndicators(), is(Arrays.asList(0, 0, 0)));
        assertThat(actual.getPlugPairs(), is(new String[0]));
    }

    @Test
    public void indicatorsNumberFormatShouldBeParsed() {
        final EnigmaSetting actual = enigmaSettingService.parse("UKW-B, I II III, ABC, 10 11 12");
        assertThat(actual.getReflector(), is("UKW-B"));
        assertThat(actual.getRotors(), is(Arrays.asList("I", "II", "III")));
        assertThat(actual.getRings(), is(Arrays.asList(0, 1, 2)));
        assertThat(actual.getIndicators(), is(Arrays.asList(10, 11, 12)));
        assertThat(actual.getPlugPairs(), is(new String[0]));
    }

    @Test
    public void indicatorsLetterFormatShouldBeParsed() {
        final EnigmaSetting actual = enigmaSettingService.parse("UKW-B, I II III, ABC, DEF");
        assertThat(actual.getReflector(), is("UKW-B"));
        assertThat(actual.getRotors(), is(Arrays.asList("I", "II", "III")));
        assertThat(actual.getRings(), is(Arrays.asList(0, 1, 2)));
        assertThat(actual.getIndicators(), is(Arrays.asList(3, 4, 5)));
        assertThat(actual.getPlugPairs(), is(new String[0]));
    }

    @Test
    public void plugWiresShouldBeParsed() {
        final EnigmaSetting actual = enigmaSettingService.parse("UKW-B, I II III, ABC, DEF, AV BS CG DL FU HZ IN KM OW RX");
        assertThat(actual.getReflector(), is("UKW-B"));
        assertThat(actual.getRotors(), is(Arrays.asList("I", "II", "III")));
        assertThat(actual.getRings(), is(Arrays.asList(0, 1, 2)));
        assertThat(actual.getIndicators(), is(Arrays.asList(3, 4, 5)));
        assertThat(actual.getPlugPairs(), is(new String[]{"AV", "BS", "CG", "DL", "FU", "HZ", "IN", "KM", "OW", "RX"}));
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = REFLECTOR_VALIDATION_MSG)
    public void emptyStringShouldBeReflectorException() {
        enigmaSettingService.parse("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ROTORS_VALIDATION_MSG)
    public void rotorsNotDefinedShouldBeException() {
        enigmaSettingService.parse("UKW-B");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ROTORS_NUMBER_VALIDATION_MSG)
    public void rotorsNumberLtMinimumShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = RINGS_NUMBER_VALIDATION_MSG)
    public void ringsNumberLetterFormatLtRotorsNumberShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, AA");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = RINGS_NUMBER_VALIDATION_MSG)
    public void ringsNumberLetterFormatGtRotorsNumberShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, AAAA");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = RINGS_NUMBER_VALIDATION_MSG)
    public void ringsNumberIntFormatLtRotorsNumberShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, 10 11");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = RINGS_NUMBER_VALIDATION_MSG)
    public void ringsNumberIntFormatGtRotorsNumberShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, 10 11 12 13");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = INDICATORS_NUMBER_VALIDATION_MSG)
    public void indicatorsNumberIntFormatLtRotorsNumberShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, AAA, 10");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = INDICATORS_NUMBER_VALIDATION_MSG)
    public void indicatorsNumberLetterFormatLtRotorsNumberShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, AAA, AA");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = INDICATORS_NUMBER_VALIDATION_MSG)
    public void indicatorsNumberLetterFormatGtRotorsNumberShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, AAA, AAAA");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = PLUGS_LEN_VALIDATION_MSG)
    public void plugDefinitionNotPairedShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, AAA, AAA, C");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = PLUGS_UNIQUE_VALIDATION_MSG + ".+")
    public void plugDuplicatedShouldBeException() {
        enigmaSettingService.parse("UKW-B, I II III, AAA, AAA, CC");
    }
}