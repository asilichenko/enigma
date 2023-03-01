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
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * Creation date: 12.10.2022
 */
public class WhenPlugboardCreate {

    private final PlugboardService plugboardService = new PlugboardService();

    @DataProvider
    public Object[][] plugsDataProvider() {
        return new Object[][]{
                {new String[]{"PO", "ML", "IU", "KJ", "NH", "YT", "GB", "VF", "RE", "DC"},
                        "AGDCRVBNUKJMLHPOQESYIFWXTZ"},
        };
    }

    @Test(dataProvider = "plugsDataProvider")
    public void lettersShouldBeSwapped(String[] plugs, String expected) {
        final Plugboard plugboard = plugboardService.createPlugboard(plugs);
        final String actual = stringOf(plugboard.getLayout());
        assertThat(actual, is(expected));
    }
}
