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
import static ua.in.asilichenko.enigma.util.PlugboardUtils.asString;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 06.11.2022
 */
public class WhenPlugboardToString {

    @DataProvider
    public Object[][] dataProvider() {
        // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
        // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z
        return new Object[][]{
                {
                        new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25},
                        ""
                },
                {
                        new int[]{0, 1, 16, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 2, 17, 18, 19, 20, 21, 22, 23, 24, 25},
                        "CQ"
                },
                {
                        new int[]{0, 1, 16, 20, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 2, 17, 18, 19, 3, 21, 22, 23, 24, 25},
                        "CQ DU"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 5, 6, 7, 8, 9, 10, 11, 12, 4, 14, 15, 2, 17, 18, 19, 3, 21, 22, 23, 24, 25},
                        "CQ DU EN"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 17, 6, 7, 8, 9, 10, 11, 12, 4, 14, 15, 2, 5, 18, 19, 3, 21, 22, 23, 24, 25},
                        "CQ DU EN FR"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 17, 23, 7, 8, 9, 10, 11, 12, 4, 14, 15, 2, 5, 18, 19, 3, 21, 22, 6, 24, 25},
                        "CQ DU EN FR GX"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 17, 23, 7, 18, 9, 10, 11, 12, 4, 14, 15, 2, 5, 8, 19, 3, 21, 22, 6, 24, 25},
                        "CQ DU EN FR GX IS"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 17, 23, 7, 18, 15, 10, 11, 12, 4, 14, 9, 2, 5, 8, 19, 3, 21, 22, 6, 24, 25},
                        "CQ DU EN FR GX IS JP"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 17, 23, 7, 18, 15, 14, 11, 12, 4, 10, 9, 2, 5, 8, 19, 3, 21, 22, 6, 24, 25},
                        "CQ DU EN FR GX IS JP KO"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 17, 23, 7, 18, 15, 14, 11, 12, 4, 10, 9, 2, 5, 8, 24, 3, 21, 22, 6, 19, 25},
                        "CQ DU EN FR GX IS JP KO TY"
                },
                {
                        new int[]{0, 1, 16, 20, 13, 17, 23, 7, 18, 15, 14, 11, 12, 4, 10, 9, 2, 5, 8, 24, 3, 25, 22, 6, 19, 21},
                        "CQ DU EN FR GX IS JP KO TY VZ"
                },
        };
    }

    @Test(dataProvider = "dataProvider")
    public void shouldBePairsOfSwappedLettersSeparatedBySpace(int[] plugboard, String expected) {
        assertThat(asString(plugboard), is(expected));
    }
}