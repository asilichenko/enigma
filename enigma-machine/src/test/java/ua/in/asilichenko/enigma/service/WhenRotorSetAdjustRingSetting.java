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
import ua.in.asilichenko.enigma.domain.RotorSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

/**
 * Creation date: 12.10.2022
 */
public class WhenRotorSetAdjustRingSetting {

    private final RotorSetService rotorSetService = new RotorSetService();

    private RotorSet dummyRotorSet(int n) {
        final List<Rotor> rotors = new ArrayList<>(n);
        IntStream.range(0, n).forEach(i -> rotors.add(mock(Rotor.class)));
        return rotorSetOf(rotors);
    }

    private RotorSet rotorSetOf(List<Rotor> rotors) {
        final RotorSet retval = mock(RotorSet.class);
        when(retval.getRotors()).thenReturn(rotors);
        return retval;
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".+rotors size: 1.+ring setting size: 3\\.")
    public void incompatibleRotorsSizeAndSettingSeizeShouldThrowException() {
        rotorSetService.adjustRings(dummyRotorSet(1), new int[]{1, 2, 3});
    }

    @DataProvider
    public Object[][] illegalRingSettingDataProvider() {
        return new Object[][]{
                {new int[]{-1, 0, 0}},
                {new int[]{0, -1, 0}},
                {new int[]{0, 0, -1}},
                {new int[]{26, 0, 0}},
                {new int[]{0, 26, 0}},
                {new int[]{0, 0, 26}},
        };
    }

    @Test(dataProvider = "illegalRingSettingDataProvider",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Incorrect ring \\d+ setting: -?\\d+\\..+")
    public void ringSettingValueOutOfRangeShouldThrowException(int[] ringSetting) {
        rotorSetService.adjustRings(dummyRotorSet(ringSetting.length), ringSetting);
    }

    @Test
    public void ringPositionsShouldBeSetToCorrespondingRotors() {
        final Rotor rotor1 = mock(Rotor.class);
        final Rotor rotor2 = mock(Rotor.class);
        final Rotor rotor3 = mock(Rotor.class);

        final int rotor1Setting = 6;
        final int rotor2Setting = 22;
        final int rotor3Setting = 14;

        rotorSetService.adjustRings(
                rotorSetOf(Arrays.asList(rotor1, rotor2, rotor3)),
                new int[]{rotor1Setting, rotor2Setting, rotor3Setting}
        );

        verify(rotor1).setRing(rotor1Setting);
        verify(rotor2).setRing(rotor2Setting);
        verify(rotor3).setRing(rotor3Setting);
    }
}
