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
import ua.in.asilichenko.enigma.domain.Rotor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;

/**
 * See https://www.cryptomuseum.com/crypto/enigma/wiring.htm
 * <p>
 * [01] / [A] ring mark = ring index 0
 * <p>
 * Visible: 01 ('A')
 * <p>
 * Notch at: 09 ('I')
 * <p>
 * Is on notch position: true
 * <p>
 * Creation date: 09.10.2022
 */
public class WhenRotorIsAtNotchPosition {

    private static final int[] WIRING_STUB = new int[LETTERS_COUNT];

    private final RotorService rotorService = new RotorService();

    @DataProvider
    public Object[][] notchPosDataProvider() {
        return new Object[][]{
                {8, 0},
                {9, 1},
                {10, 2},
                {11, 3},
                {12, 4},
                {13, 5},
                {14, 6},
                {15, 7},
                {16, 8},
                {17, 9},
                {18, 10},
                {19, 11},
                {20, 12},
                {21, 13},
                {22, 14},
                {23, 15},
                {24, 16},
                {25, 17},
                {0, 18},
                {1, 19},
                {2, 20},
                {3, 21},
                {4, 22},
                {5, 23},
                {6, 24},
                {7, 25},
        };
    }

    private void test(Rotor rotor, int rotorPosExpected) {
        for (int rotorPos = 0; rotorPos < LETTERS_COUNT; rotorPos++) {
            rotor.setIndicator(rotorPos);
            assertThat(rotorService.isInNotchPosition(rotor), is(rotorPos == rotorPosExpected));
        }
    }

    @Test(dataProvider = "notchPosDataProvider")
    public void notchPosMinus8Mod26ShouldBeRotorPos(int notchPos, int rotorPosExpected) {
        test(new Rotor("", WIRING_STUB, WIRING_STUB, notchPos), rotorPosExpected);
    }

    @Test(dependsOnMethods = "notchPosMinus8Mod26ShouldBeRotorPos", dataProvider = "notchPosDataProvider")
    public void ringShiftNotAffect(int notchPos, int rotorPosExpected) {
        final Rotor rotor = new Rotor("", WIRING_STUB, WIRING_STUB, notchPos);
        for (int ringShift = 0; ringShift < LETTERS_COUNT; ringShift++) {
            rotor.setRing(ringShift);
            test(rotor, rotorPosExpected);
        }
    }
}
