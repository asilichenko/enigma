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
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * Creation date: 09.10.2022
 */
public class WhenPlugboardSwapLetters {

    private final PlugboardService plugboardService = new PlugboardService();

    @DataProvider
    public Object[][] swapPairDataProvider() {
        return new Object[][]{
                {"PO", "ABCDEFGHIJKLMNPOQRSTUVWXYZ"},
                {"ML", "ABCDEFGHIJKMLNOPQRSTUVWXYZ"},
                {"IU", "ABCDEFGHUJKLMNOPQRSTIVWXYZ"},
                {"KJ", "ABCDEFGHIKJLMNOPQRSTUVWXYZ"},
                {"NH", "ABCDEFGNIJKLMHOPQRSTUVWXYZ"},
                {"YT", "ABCDEFGHIJKLMNOPQRSYUVWXTZ"},
                {"GB", "AGCDEFBHIJKLMNOPQRSTUVWXYZ"},
                {"VF", "ABCDEVGHIJKLMNOPQRSTUFWXYZ"},
                {"RE", "ABCDRFGHIJKLMNOPQESTUVWXYZ"},
                {"DC", "ABDCEFGHIJKLMNOPQRSTUVWXYZ"},
        };
    }

    @Test(dataProvider = "swapPairDataProvider")
    public void twoLettersShouldBeSwapped(String plugWire, String expected) {
        final Plugboard plugboard = new Plugboard();
        plugboardService.connectPlugPair(plugboard, plugWire);
        final int[] layout = plugboard.getLayout();
        final String actual = stringOf(layout);
        assertThat(actual, is(expected));
    }

    @Test
    public void connectPlugPairShouldSwapTheseLettersOnPlugboard() {
        final Plugboard plugboard = new Plugboard();

        final int letter1 = indexOf('F');
        final int letter2 = indexOf('K');

        plugboardService.connectPlugPair(plugboard, "FK");

        final int[] layout = plugboard.getLayout();

        final int actual1 = layout[letter1];
        assertThat(actual1, is(letter2));

        final int actual2 = layout[letter2];
        assertThat(actual2, is(letter1));
    }

    @Test
    public void plugboardCreatedForArrayOfPairsShouldSwapThesePairs() {
        final String[] plugPairs = new String[]{"PO", "ML", "IU", "KJ", "NH", "YT", "GB", "VF", "RE", "DC"};
        final Plugboard plugboard = plugboardService.createPlugboard(plugPairs);

        for (String pair : plugPairs) {
            final int[] plugWire = indexOf(pair);
            final int letter1 = plugWire[0];
            final int letter2 = plugWire[1];

            final int actual1 = plugboardService.cipher(plugboard, letter2);
            assertThat(actual1, is(letter1));

            final int actual2 = plugboardService.cipher(plugboard, letter1);
            assertThat(actual2, is(letter2));
        }
    }
}