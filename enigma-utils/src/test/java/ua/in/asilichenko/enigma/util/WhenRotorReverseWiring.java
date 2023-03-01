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

package ua.in.asilichenko.enigma.util;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.RotorUtils.reverse;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 13.11.2022
 */
public class WhenRotorReverseWiring {

    private static final String WIRING_FORWARD = "BDFHJLCPRTXVZNYEIWGAKMUSQO"; // wiring III
    private static final String WIRING_BACKWARD = "TAGBPCSDQEUFVNZHYIXJWLRKOM";

    @Test
    public void mappingLettersAndMappedLettersShouldBeSwapped() {
        final int[] input = indexOf(WIRING_FORWARD);
        final int[] actual = reverse(input);
        assertThat(stringOf(actual), is(WIRING_BACKWARD));
    }
}
