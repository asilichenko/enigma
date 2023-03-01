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
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.11.2022
 */
public class WhenIndexOfString {
    // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
    // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z

    @DataProvider
    public Object[][] lettersDataProvider() {
        return new Object[][]{
                {"A", 0}, {"B", 1}, {"C", 2}, {"D", 3}, {"E", 4},
                {"F", 5}, {"G", 6}, {"H", 7}, {"I", 8}, {"J", 9},
                {"K", 10}, {"L", 11}, {"M", 12}, {"N", 13}, {"O", 14},
                {"P", 15}, {"Q", 16}, {"R", 17}, {"S", 18}, {"T", 19},
                {"U", 20}, {"V", 21}, {"W", 22}, {"X", 23}, {"Y", 24},
                {"Z", 25},
                //
                {"HELLOWORLD", new int[]{7, 4, 11, 11, 14, 22, 14, 17, 11, 3}},
        };
    }

    @Test(dataProvider = "lettersDataProvider")
    public void stringShouldBeArrayOfLetterIndexes(String in, int... expected) {
        assertThat(indexOf(in), is(expected));
    }
}
