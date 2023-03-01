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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.letterOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 11.10.2022
 */
public class WhenEnigmaCipher {

    private static final int DUMMY_NOTCH = -1;

    private EnigmaMachineService enigmaMachineService;
    private RotorService rotorService;

    @BeforeMethod
    public void setUp() {
        rotorService = spy(new RotorService());

        final RotorSetService rotorSetService = new RotorSetService();
        rotorSetService.setRotorService(rotorService);

        enigmaMachineService = new EnigmaMachineService();
        enigmaMachineService.setPlugboardService(new PlugboardService());
        enigmaMachineService.setRotorService(rotorService);
        enigmaMachineService.setRotorSetService(rotorSetService);
    }

    private RotorSet createSpyRotorSet() {
        return spy(new RotorSet(new Reflector("UKW-B", indexOf("YRUHQSLDPXNGOKMIEBFZCWVJAT")),
                Arrays.asList(
                        new Rotor("I", indexOf("EKMFLGDQVZNTOWYHXUSPAIBRCJ"), indexOf("UWYGADFPVZBECKMTHXSLRINQOJ"), DUMMY_NOTCH),
                        new Rotor("II", indexOf("AJDKSIRUXBLHWTMCQGZNPYFVOE"), indexOf("AJPCZWRLFBDKOTYUQGENHXMIVS"), DUMMY_NOTCH),
                        new Rotor("III", indexOf("BDFHJLCPRTXVZNYEIWGAKMUSQO"), indexOf("TAGBPCSDQEUFVNZHYIXJWLRKOM"), DUMMY_NOTCH)
                )));
    }

    private EnigmaMachine createEnigmaMachine(RotorSet rotorSet) {
        final EnigmaMachine retval = new EnigmaMachine(rotorSet, new Plugboard());
        enigmaMachineService.disableRotorsIncrement(retval);
        return retval;
    }

    /*
    ABCDEFGHIJKLMNOPQRSTUVWXYZ < alphabet
    -
    BDFHJLCPRTXVZNYEIWGAKMUSQO < right
    AJDKSIRUXBLHWTMCQGZNPYFVOE < mid
    EKMFLGDQVZNTOWYHXUSPAIBRCJ < left

    YRUHQSLDPXNGOKMIEBFZCWVJAT < reflector

    UWYGADFPVZBECKMTHXSLRINQOJ < left reverse
    AJPCZWRLFBDKOTYUQGENHXMIVS < mid reverse
    TAGBPCSDQEUFVNZHYIXJWLRKOM < right reverse
    -
    ABCDEFGHIJKLMNOPQRSTUVWXYZ < alphabet

    A> B-J-Z T L-K-U >U
    Z> O-M-O M C-P-H >H
     */
    @DataProvider
    public Object[][] zeroPositionDataProvider() {
        return new Object[][]{
                {new char[]{'A', 'B', 'J', 'Z', 'T', 'L', 'K', 'U'}},
                {new char[]{'Z', 'O', 'M', 'O', 'M', 'C', 'P', 'H'}}
        };
    }

    @Test(dataProvider = "zeroPositionDataProvider")
    public void zeroPositionShouldBeAccordingToWiring(char[] chain) {
        final RotorSet rotorSet = createSpyRotorSet();
        final EnigmaMachine enigmaMachine = createEnigmaMachine(rotorSet);
        final List<Rotor> rotors = rotorSet.getRotors();
        final Rotor rotor1 = rotors.get(0);
        final Rotor rotor2 = rotors.get(1);
        final Rotor rotor3 = rotors.get(2);

        final int actual = enigmaMachineService.cipher(enigmaMachine, indexOf(chain[0]));

        verify(rotorService).cipherForward(rotor3, indexOf(chain[0]));
        verify(rotorService).cipherForward(rotor2, indexOf(chain[1]));
        verify(rotorService).cipherForward(rotor1, indexOf(chain[2]));

        verify(rotorSet).reflect(indexOf(chain[3]));

        verify(rotorService).cipherBackward(rotor1, indexOf(chain[4]));
        verify(rotorService).cipherBackward(rotor2, indexOf(chain[5]));
        verify(rotorService).cipherBackward(rotor3, indexOf(chain[6]));

        assertThat(letterOf(actual), is(chain[7]));
    }

    /*
    ABCDEFGHIJKLMNOPQRSTUVWXYZ < alphabet
    -
    BDFHJLCPRTXVZNYEIWGAKMUSQO < right
    AJDKSIRUXBLHWTMCQGZNPYFVOE < mid
    EKMFLGDQVZNTOWYHXUSPAIBRCJ < left

    YRUHQSLDPXNGOKMIEBFZCWVJAT < reflector

    UWYGADFPVZBECKMTHXSLRINQOJ < left reverse
    AJPCZWRLFBDKOTYUQGENHXMIVS < mid reverse
    TAGBPCSDQEUFVNZHYIXJWLRKOM < right reverse
    -
    ABCDEFGHIJKLMNOPQRSTUVWXYZ < alphabet

    001 A> (+1=[B>D]-1=C)>D>F |S| S>E>(+1=[F>C]-1=B)
    010 B> D>(+1=[E>S]-1=R)>U |C| Y>(+1=[Z>S]-1=R)>I
    100 C> F>I>(+1=[J>Z]-1=Y) |A| (+1=[B>W]-1=V)>X>K
     */
    @DataProvider
    public Object[][] notZeroPositionDataProvider() {
        return new Object[][]{
                // input, after rotor3, after 2, after 1, after reflector, after 1 reverse, after 2 reverse, after 3 reverse
                {0, 0, 1, new char[]{'A', 'C', 'D', 'F', 'S', 'S', 'E', 'B'}},
                {0, 1, 0, new char[]{'B', 'D', 'R', 'U', 'C', 'Y', 'R', 'I'}},
                {1, 0, 0, new char[]{'C', 'F', 'I', 'Y', 'A', 'V', 'X', 'K'}},
        };
    }

    @Test(dataProvider = "notZeroPositionDataProvider")
    public void shiftedRotorShouldShiftInputAndOutputLetters(int indicator1, int indicator2, int indicator3, char[] chain) {
        final RotorSet rotorSet = createSpyRotorSet();
        final EnigmaMachine enigmaMachine = createEnigmaMachine(rotorSet);
        final List<Rotor> rotors = rotorSet.getRotors();
        final Rotor rotor1 = rotors.get(0);
        final Rotor rotor2 = rotors.get(1);
        final Rotor rotor3 = rotors.get(2);

        rotor1.setIndicator(indicator1);
        rotor2.setIndicator(indicator2);
        rotor3.setIndicator(indicator3);

        final int actual = enigmaMachineService.cipher(enigmaMachine, indexOf(chain[0]));

        verify(rotorService).cipherForward(rotor3, indexOf(chain[0]));
        verify(rotorService).cipherForward(rotor2, indexOf(chain[1]));
        verify(rotorService).cipherForward(rotor1, indexOf(chain[2]));

        verify(rotorSet).reflect(indexOf(chain[3]));

        verify(rotorService).cipherBackward(rotor1, indexOf(chain[4]));
        verify(rotorService).cipherBackward(rotor2, indexOf(chain[5]));
        verify(rotorService).cipherBackward(rotor3, indexOf(chain[6]));

        assertThat(letterOf(actual), is(chain[7]));
    }
}
