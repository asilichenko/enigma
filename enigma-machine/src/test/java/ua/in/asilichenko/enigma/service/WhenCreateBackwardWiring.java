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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 29.10.2022
 */
public class WhenCreateBackwardWiring {

    private final RotorService rotorService = new RotorService();

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                // A -> E <> E -> A
                //ABCDEFGHIJKLMNOPQRSTUVWXYZ    ABCDEFGHIJKLMNOPQRSTUVWXYZ
                {"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "UWYGADFPVZBECKMTHXSLRINQOJ"}, // I
                {"AJDKSIRUXBLHWTMCQGZNPYFVOE", "AJPCZWRLFBDKOTYUQGENHXMIVS"}, // II
                {"BDFHJLCPRTXVZNYEIWGAKMUSQO", "TAGBPCSDQEUFVNZHYIXJWLRKOM"}, // III
        };
    }

    @Test(dataProvider = "getData")
    public void lettersMappingShouldBeReversed(String forwardWiring, String expected) {
        final int[] wiring = indexOf(forwardWiring);
        final int[] actual = rotorService.backwardWiringOf(wiring);
        assertThat(stringOf(actual), is(expected));
    }
}