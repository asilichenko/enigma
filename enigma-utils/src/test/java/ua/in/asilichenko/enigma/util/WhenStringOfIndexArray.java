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
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.11.2022
 */
public class WhenStringOfIndexArray {
    // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
    // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z

    @DataProvider
    public Object[][] lettersDataProvider() {
        return new Object[][]{
                {0, "A"}, {1, "B"}, {2, "C"}, {3, "D"}, {4, "E"},
                {5, "F"}, {6, "G"}, {7, "H"}, {8, "I"}, {9, "J"},
                {10, "K"}, {11, "L"}, {12, "M"}, {13, "N"}, {14, "O"},
                {15, "P"}, {16, "Q"}, {17, "R"}, {18, "S"}, {19, "T"},
                {20, "U"}, {21, "V"}, {22, "W"}, {23, "X"}, {24, "Y"},
                {25, "Z"},
        };
    }

    @Test(dataProvider = "lettersDataProvider")
    public void oneLetterShouldBeStringOfThisLetter(int in, String expected) {
        assertThat(stringOf(new int[]{in}), is(expected));
    }

    @Test
    public void severalLettersShouldBeConvertedToText() {
        final int[] text = {7, 4, 11, 11, 14, 22, 14, 17, 11, 3};
        assertThat(stringOf(text), is("HELLOWORLD"));
    }
}
