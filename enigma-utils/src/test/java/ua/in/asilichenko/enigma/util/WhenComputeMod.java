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
import static ua.in.asilichenko.enigma.util.LetterUtils.mod;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 09.10.2022
 */
public class WhenComputeMod {

    @DataProvider
    public Object[][] withinDataProvider() {
        return new Object[][]{
                {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9},
                {10}, {11}, {12}, {13}, {14}, {15}, {16}, {17}, {18}, {19},
                {20}, {21}, {22}, {23}, {24}, {25},
        };
    }

    @Test(dataProvider = "withinDataProvider")
    public void withinLimitShouldBeSameValue(int val) {
        assertThat(mod(val), is(val));
    }

    @DataProvider
    public Object[][] exceedingDataProvider() {
        return new Object[][]{
                {26, 0}, {27, 1}, {28, 2}, {29, 3}, {30, 4}, {31, 5}, {32, 6}, {33, 7}, {34, 8}, {35, 9},
                {36, 10}, {37, 11}, {38, 12}, {39, 13}, {40, 14}, {41, 15}, {42, 16}, {43, 17}, {44, 18}, {45, 19},
                {46, 20}, {47, 21}, {48, 22}, {49, 23}, {50, 24}, {51, 25},
        };
    }

    @Test(dataProvider = "exceedingDataProvider")
    public void exceedingValueShouldBeModulo(int in, int expected) {
        assertThat(mod(in), is(expected));
    }

    @DataProvider
    public Object[][] negativeDataProvider() {
        return new Object[][]{
                {-25, 1}, {-24, 2}, {-23, 3}, {-22, 4}, {-21, 5}, {-20, 6}, {-19, 7}, {-18, 8}, {-17, 9},
                {-16, 10}, {-15, 11}, {-14, 12}, {-13, 13}, {-12, 14}, {-11, 15}, {-10, 16}, {-9, 17}, {-8, 18}, {-7, 19},
                {-6, 20}, {-5, 21}, {-4, 22}, {-3, 23}, {-2, 24}, {-1, 25},
        };
    }

    @Test(dataProvider = "negativeDataProvider")
    public void negativeValueShouldBeModuloFromEnd(int in, int expected) {
        assertThat(mod(in), is(expected));
    }
}
