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

package ua.in.asilichenko.enigma.hillclimbing.service;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.dao.ReflectorThinDao;
import ua.in.asilichenko.enigma.dao.WheelDao;
import ua.in.asilichenko.enigma.dao.WheelExtraDao;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.plugboardWith;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 12.11.2022
 */
public class WhenEnigmaCipher {

    private static final int[] INPUT = indexOf("VONGRUPPEWEST");

    private final EnigmaServiceM3 enigmaServiceM3 = new EnigmaServiceM3();
    private final EnigmaServiceM4 enigmaServiceM4 = new EnigmaServiceM4();

    @BeforeClass
    public void setUp() {
        enigmaServiceM3.setReflectorDao(new ReflectorDao());
        enigmaServiceM3.setWheelDao(new WheelDao());

        enigmaServiceM4.setReflectorDao(new ReflectorThinDao());
        enigmaServiceM4.setWheelExtraDao(new WheelExtraDao());
        enigmaServiceM4.setWheelDao(new WheelDao());
    }

    @Test
    public void messageShouldBeCipheredOverEnigmaM3() {
        final String expected = "JZDREZMQJNLMQ";
        final int[] actual = enigmaServiceM3.cipher(INPUT,
                "UKW-C",
                Arrays.asList("III", "II", "VIII"),
                10, 7,
                indexOf("PFT"),
                plugboardWith("CQ DU EN FR GX IS JP KO TY VZ".split(" "))
        );
        assertThat(stringOf(actual), is(expected));
    }

    @Test
    public void messageShouldBeCipheredOverEnigmaM4() {
        final String expected = "CTCAMHSUMLXJY";
        final int[] actual = enigmaServiceM4.cipher(INPUT,
                "B-Thin",
                Arrays.asList("Beta", "III", "II", "VIII"),
                10, 7,
                indexOf("QPFT"),
                plugboardWith("CQ DU EN FR GX IS JP KO TY VZ".split(" "))
        );
        assertThat(stringOf(actual), is(expected));
    }
}
