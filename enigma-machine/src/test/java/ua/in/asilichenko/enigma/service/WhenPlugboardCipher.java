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
import ua.in.asilichenko.enigma.domain.Plugboard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.letterOf;

/**
 * Creation date: 12.10.2022
 */
public class WhenPlugboardCipher {

    private static final String PLUGBOARD_LAYOUT = "AGDCRVBNUKJMLHPOQESYIFWXTZ";

    private final PlugboardService plugboardService = new PlugboardService();

    private Plugboard createPlugboard() {
        return new Plugboard(indexOf(PLUGBOARD_LAYOUT));
    }

    @DataProvider
    public Object[][] swappedLettersDataProvider() {
        return new Object[][]{
                {'P', 'O'}, {'O', 'P'},
                {'M', 'L'}, {'L', 'M'},
                {'I', 'U'}, {'U', 'I'},
                {'K', 'J'}, {'J', 'K'},
                {'N', 'H'}, {'H', 'N'},
                {'Y', 'T'}, {'T', 'Y'},
                {'G', 'B'}, {'B', 'G'},
                {'V', 'F'}, {'F', 'V'},
                {'R', 'E'}, {'E', 'R'},
                {'D', 'C'}, {'C', 'D'},
        };
    }

    @Test(dataProvider = "swappedLettersDataProvider")
    public void lettersShouldBeSwapped(char letter, char expected) {
        final Plugboard plugboard = createPlugboard();
        final int actual = plugboardService.cipher(plugboard, indexOf(letter));
        assertThat(letterOf(actual), is(expected));
    }

    @DataProvider
    public Object[][] notSwappedLettersDataProvider() {
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // AGDCRVBNUKJMLHPOQESYIFWXTZ
        return new Object[][]{
                {'A'}, {'Q'}, {'S'}, {'W'}, {'X'}, {'Z'},
        };
    }

    @Test(dataProvider = "notSwappedLettersDataProvider")
    public void lettersShouldBeInPlace(char letter) {
        final Plugboard plugboard = createPlugboard();
        final int actual = plugboardService.cipher(plugboard, indexOf(letter));
        assertThat(letterOf(actual), is(letter));
    }
}
