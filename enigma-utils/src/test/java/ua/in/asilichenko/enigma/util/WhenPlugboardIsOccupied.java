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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.isOccupied;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.11.2022
 */
public class WhenPlugboardIsOccupied {

    private static final int[] plugboard = new int[]{0, 1, 2, 5, 4, 3};

    @DataProvider
    public Object[][] dataProvider() {
        return new Object[][]{
                {0, false},
                {1, false},
                {2, false},
                {3, true},
                {4, false},
                {5, true},
        };
    }

    @Test(dataProvider = "dataProvider")
    public void swappedLettersShouldBeOccupied(int letter, boolean expected) {
        assertThat(isOccupied(plugboard, letter), is(expected));
    }
}
