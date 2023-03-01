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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.dao.WheelDao;
import ua.in.asilichenko.enigma.domain.EnigmaMachine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 09.11.2022
 */
public class EnigmaMachineTest {

    private EnigmaMachineService enigmaMachineService;

    @BeforeMethod
    public void setUp() {
        enigmaMachineService = new EnigmaMachineService();
        enigmaMachineService.setEnigmaSettingService(new EnigmaSettingService());

        final ReflectorService reflectorService = new ReflectorService();
        reflectorService.setReflectorDao(new ReflectorDao());

        final RotorService rotorService = new RotorService();
        rotorService.setWheelDao(new WheelDao());

        final RotorSetService rotorSetService = new RotorSetService();
        rotorSetService.setRotorService(rotorService);
        rotorSetService.setReflectorService(reflectorService);

        enigmaMachineService.setRotorSetService(rotorSetService);
        enigmaMachineService.setRotorService(rotorService);
        enigmaMachineService.setPlugboardService(new PlugboardService());
    }

    @Test
    public void rotorsTest() {
        final String settingString = "UKW-B, I II III";
        final String text = "HELLOWORLD";
        final String expected = "ILBDAAMTAZ";

        final EnigmaMachine enigmaMachine = enigmaMachineService.build(settingString);
        final String actual = enigmaMachineService.cipher(enigmaMachine, text);

        assertThat(actual, is(expected));
    }

    @Test
    public void ringSettingTest() {
        final String setting = "UKW-C, III II VIII, 0 10 7";
        final String input = "VONGRUPPEWEST";
        final String expected = "TYOSZWCUFLZVM";

        final EnigmaMachine enigmaMachine = enigmaMachineService.build(setting);
        final String actual = enigmaMachineService.cipher(enigmaMachine, input);

        assertThat(actual, is(expected));
    }

    @Test
    public void indicatorsTest() {
        final String setting = "UKW-C, III II VIII, 0 10 7, PFT";
        final String input = "VONGRUPPEWEST";
        final String expected = "LBCJMMQBAEJUG";

        final EnigmaMachine enigmaMachine = enigmaMachineService.build(setting);
        final String actual = enigmaMachineService.cipher(enigmaMachine, input);

        assertThat(actual, is(expected));
    }

    @Test
    public void onePlugPairTest() {
        final String setting = "UKW-C, III II VIII, 0 10 7, PFT, CQ";
        final String input = "VONGRUPPEWEST";
        final String expected = "LBQJMMCBAEJUG";

        final EnigmaMachine enigmaMachine = enigmaMachineService.build(setting);
        final String actual = enigmaMachineService.cipher(enigmaMachine, input);

        assertThat(actual, is(expected));
    }

    @Test
    public void tenPlugPairsTest() {
        final String setting = "UKW-C, III II VIII, 0 10 7, PFT, CQ DU EN FR GX IS JP KO TY VZ";
        final String input = "VONGRUPPEWEST";
        final String expected = "JZDREZMQJNLMQ";

        final EnigmaMachine enigmaMachine = enigmaMachineService.build(setting);
        final String actual = enigmaMachineService.cipher(enigmaMachine, input);

        assertThat(actual, is(expected));
    }
}
