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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.domain.Reflector;
import ua.in.asilichenko.enigma.domain.Rotor;
import ua.in.asilichenko.enigma.domain.RotorSet;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.letterOf;

/**
 * Creation date: 10.10.2022
 */
public class WhenRotorsSetCipher {

    private static final int[] REFLECTOR = indexOf("YRUHQSLDPXNGOKMIEBFZCWVJAT");
    private static final int[] WIRING_FORWARD = indexOf("EKMFLGDQVZNTOWYHXUSPAIBRCJ");
    private static final int[] WIRING_BACKWARD = indexOf("UWYGADFPVZBECKMTHXSLRINQOJ");
    private static final int DUMMY_NOTCH = -1;

    private RotorSetService rotorSetService;

    @BeforeClass
    public void setUp() {
        rotorSetService = new RotorSetService();
        rotorSetService.setRotorService(new RotorService());
    }

    private RotorSet createRotorSet() {
        return new RotorSet(
                new Reflector("UKW-B", REFLECTOR),
                singletonList(new Rotor("I", WIRING_FORWARD, WIRING_BACKWARD, DUMMY_NOTCH))
        );
    }

    /*
     Input -> Rotor
     A BCDEFGHIJKLMNOPQRSTUVWXYZ
     E KMFLGDQVZNTOWYHXUSPAIBRCJ
     A -> E

     Rotor -> Reflector
     ABCD E FGHIJKLMNOPQRSTUVWXYZ
     YRUH Q SLDPXNGOKMIEBFZCWVJAT
     E -> Q

     Reflector -> Rotor
     ABCDEFGHIJKLMNOP Q RSTUVWXYZ
     UWYGADFPVZBECKMT H XSLRINQOJ
     Q -> H

     Result:
     A -> H
     */
    @DataProvider
    public Object[][] encodeDataProvider() {
        return new Object[][]{
                {'A', 'H'}, // A -> E -> |Q| -> H
                {'B', 'K'}, // B -> K -> |N| -> K
                {'C', 'M'}, // C -> M -> |O| -> M
                {'D', 'S'}, // D -> F -> |S| -> S
                {'E', 'F'}, // E -> L -> |G| -> F
                {'F', 'E'}, // F -> G -> |L| -> E
                {'G', 'P'}, // G -> D -> |H| -> P
                {'H', 'A'}, // H -> Q -> |E| -> A
                {'I', 'N'}, // I -> V -> |W| -> N
                {'J', 'L'}, // J -> Z -> |T| -> L
                {'K', 'B'}, // K -> N -> |K| -> B
                {'L', 'J'}, // L -> T -> |Z| -> J
                {'M', 'C'}, // M -> O -> |M| -> C
                {'N', 'I'}, // N -> W -> |V| -> I
                {'O', 'U'}, // O -> Y -> |A| -> U
                {'P', 'G'}, // P -> H -> |D| -> G
                {'Q', 'Z'}, // Q -> X -> |J| -> Z
                {'R', 'Y'}, // R -> U -> |C| -> Y
                {'S', 'D'}, // S -> S -> |F| -> D
                {'T', 'V'}, // T -> P -> |I| -> V
                {'U', 'O'}, // U -> A -> |Y| -> O
                {'V', 'T'}, // V -> I -> |P| -> T
                {'W', 'X'}, // W -> B -> |R| -> X
                {'X', 'W'}, // X -> R -> |B| -> W
                {'Y', 'R'}, // Y -> C -> |U| -> R
                {'Z', 'Q'}  // Z -> J -> |X| -> Q
        };
    }

    @Test(dataProvider = "encodeDataProvider")
    public void letterEncodeShouldBeEncodedLetter(char input, char expected) {
        final RotorSet rotorSet = createRotorSet();
        final int actual = rotorSetService.cipher(rotorSet, indexOf(input));
        assertThat(letterOf(actual), is(expected));
    }

    @DataProvider
    public Object[][] decodeDataProvider() {
        return new Object[][]{
                {'H', 'A'}, // H -> Q -> |E| -> A
                {'K', 'B'}, // K -> N -> |K| -> B
                {'M', 'C'}, // M -> O -> |M| -> C
                {'S', 'D'}, // S -> S -> |F| -> D
                {'F', 'E'}, // F -> G -> |L| -> E
                {'E', 'F'}, // E -> L -> |G| -> F
                {'P', 'G'}, // P -> H -> |D| -> G
                {'A', 'H'}, // A -> E -> |Q| -> H
                {'N', 'I'}, // N -> W -> |V| -> I
                {'L', 'J'}, // L -> T -> |Z| -> J
                {'B', 'K'}, // B -> K -> |N| -> K
                {'J', 'L'}, // J -> Z -> |T| -> L
                {'C', 'M'}, // C -> M -> |O| -> M
                {'I', 'N'}, // I -> V -> |W| -> N
                {'U', 'O'}, // U -> A -> |Y| -> O
                {'G', 'P'}, // G -> D -> |H| -> P
                {'Z', 'Q'}, // Z -> J -> |X| -> Q
                {'Y', 'R'}, // Y -> C -> |U| -> R
                {'D', 'S'}, // D -> F -> |S| -> S
                {'V', 'T'}, // V -> I -> |P| -> T
                {'O', 'U'}, // O -> Y -> |A| -> U
                {'T', 'V'}, // T -> P -> |I| -> V
                {'X', 'W'}, // X -> R -> |B| -> W
                {'W', 'X'}, // W -> B -> |R| -> X
                {'R', 'Y'}, // R -> U -> |C| -> Y
                {'Q', 'Z'}  // Q -> X -> |J| -> Z
        };
    }

    @Test(dataProvider = "decodeDataProvider")
    public void encodedLetterEncodeShouldBeDecodedLetter(char encodedCh, char expected) {
        final RotorSet rotorSet = createRotorSet();
        final int actual = rotorSetService.cipher(rotorSet, indexOf(encodedCh));
        assertThat(letterOf(actual), is(expected));
    }
}
