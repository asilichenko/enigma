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
import ua.in.asilichenko.enigma.domain.Rotor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.letterOf;

/**
 * Creation date: 10.10.2022
 */
public class WhenRotorEncipherBackward {

    private static final int DUMMY_NOTCH = -1;
    // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
    // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z

    private static final String WIRING_FORWARD = "BDFHJLCPRTXVZNYEIWGAKMUSQO"; // wiring III
    private static final String WIRING_BACKWARD = "TAGBPCSDQEUFVNZHYIXJWLRKOM";


    // BDFHJLCPRTXVZNYEIWGAKMUSQO
    // ABCDEFGHIJKLMNOPQRSTUVWXYZ

    private RotorService rotorService;
    private Rotor rotor;

    @BeforeMethod
    public void setUp() {
        rotorService = spy(new RotorService());
        rotor = spy(new Rotor("", indexOf(WIRING_FORWARD), indexOf(WIRING_BACKWARD), DUMMY_NOTCH));
    }

    @Test
    public void testForwardWiring() {
        final int[] actual = rotorService.forwardWiringOf(WIRING_FORWARD);
        assertThat(actual, is(new int[]{1, 3, 5, 7, 9, 11, 2, 15, 17, 19, 23, 21, 25, 13, 24, 4, 8, 22, 6, 0, 10, 12, 20, 18, 16, 14}));
    }

    @Test
    public void testBackwardWiring() {
        //                             0  1  2  3  4
        final int[] wiring = new int[]{2, 4, 1, 0, 3};
        final int[] expected = new int[]{3, 2, 0, 4, 1};

        assertThat(rotorService.backwardWiringOf(wiring), is(expected));
    }

    @DataProvider
    public Object[][] rotorZeroIndexDataProvider() {
        // BDFHJLCPRTXVZNYEIWGAKMUSQO
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        return new Object[][]{
                {'B', 'A'},
                {'D', 'B'},
                {'F', 'C'},
                {'H', 'D'},
                {'J', 'E'},
                {'L', 'F'},
                {'C', 'G'},
                {'P', 'H'},
                {'R', 'I'},
                {'T', 'J'},
                {'X', 'K'},
                {'V', 'L'},
                {'Z', 'M'},
                {'N', 'N'},
                {'Y', 'O'},
                {'E', 'P'},
                {'I', 'Q'},
                {'W', 'R'},
                {'G', 'S'},
                {'A', 'T'},
                {'K', 'U'},
                {'M', 'V'},
                {'U', 'W'},
                {'S', 'X'},
                {'Q', 'Y'},
                {'O', 'Z'},
        };
    }

    @Test(dataProvider = "rotorZeroIndexDataProvider")
    public void notShiftedRotorShouldReturnLetterAccordingToWiring(char letterOnOutputSide, char expected) {
        final int actual = rotorService.cipherBackward(rotor, indexOf(letterOnOutputSide));
        assertThat(letterOf(actual), is(expected));
    }

    @DataProvider
    public Object[][] rotorShiftedPosDataProvider() {
        // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
        // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z
        // B  D  F  H  J  L  C  P  R  T  X  V  Z  N  Y  E  I  W  G  A  K  M  U  S  Q  O
        return new Object[][]{
                {0, 'A', 'T'},  //  0 + A( 0) =  0 mod =  0(A) -> T(19) -  0 = 19  mod = 19(T)
                {1, 'B', 'F'},  //  1 + B( 1) =  2 mod =  2(C) -> G( 6) -  1 =  5  mod =  5(F)
                {2, 'C', 'N'},  //  2 + C( 2) =  4 mod =  4(E) -> P(15) -  2 = 13  mod = 13(N)
                {3, 'D', 'P'},  //  3 + D( 3) =  6 mod =  6(G) -> S(18) -  3 = 15  mod = 15(P)
                {4, 'E', 'M'},  //  4 + E( 4) =  8 mod =  8(I) -> Q(16) -  4 = 12  mod = 12(M)
                {5, 'F', 'P'},  //  5 + F( 5) = 10 mod = 10(K) -> U(20) -  5 = 15  mod = 15(P)
                {6, 'G', 'P'},  //  6 + G( 6) = 12 mod = 12(M) -> V(21) -  6 = 15  mod = 15(P)
                {7, 'H', 'S'},  //  7 + H( 7) = 14 mod = 14(O) -> Z(25) -  7 = 18  mod = 18(S)
                {8, 'I', 'Q'},  //  8 + I( 8) = 16 mod = 16(Q) -> Y(24) -  8 = 16  mod = 16(Q)
                {9, 'J', 'O'},  //  9 + J( 9) = 18 mod = 18(S) -> X(23) -  9 = 14  mod = 14(O)
                {10, 'K', 'M'}, // 10 + K(10) = 20 mod = 20(U) -> W(22) - 10 = 12  mod = 12(M)
                {11, 'L', 'G'}, // 11 + L(11) = 22 mod = 22(W) -> R(17) - 11 =  6  mod =  6(G)
                {12, 'M', 'C'}, // 12 + M(12) = 24 mod = 24(Y) -> O(14) - 12 =  2  mod =  2(C)
                {13, 'N', 'G'}, // 13 + N(13) = 26 mod =  0(A) -> T(19) - 13 =  6  mod =  6(G)
                {14, 'O', 'S'}, // 14 + O(14) = 28 mod =  2(C) -> G( 6) - 14 = -8  mod = 18(S)
                {15, 'P', 'A'}, // 15 + P(15) = 30 mod =  4(E) -> P(15) - 15 =  0  mod =  0(A)
                {16, 'Q', 'C'}, // 16 + Q(16) = 32 mod =  6(G) -> S(18) - 16 =  2  mod =  2(C)
                {17, 'R', 'Z'}, // 17 + R(17) = 34 mod =  8(I) -> Q(16) - 17 = -1  mod = 25(Z)
                {18, 'S', 'C'}, // 18 + S(18) = 36 mod = 10(K) -> U(20) - 18 =  2  mod =  2(C)
                {19, 'T', 'C'}, // 19 + T(19) = 38 mod = 12(M) -> V(21) - 19 =  2  mod =  2(C)
                {20, 'U', 'F'}, // 20 + U(20) = 40 mod = 14(O) -> Z(25) - 20 =  5  mod =  5(F)
                {21, 'V', 'D'}, // 21 + V(21) = 42 mod = 16(Q) -> Y(24) - 21 =  3  mod =  3(D)
                {22, 'W', 'B'}, // 22 + W(22) = 44 mod = 18(S) -> X(23) - 22 =  1  mod =  1(B)
                {23, 'X', 'Z'}, // 23 + X(23) = 46 mod = 20(U) -> W(22) - 23 = -1  mod = 25(Z)
                {24, 'Y', 'T'}, // 24 + Y(24) = 48 mod = 22(W) -> R(17) - 24 = -7  mod = 19(T)
                {25, 'Z', 'P'}, // 25 + Z(25) = 50 mod = 24(Y) -> O(14) - 25 = -11 mod = 15(P)
        };
    }

    @Test(dataProvider = "rotorShiftedPosDataProvider")
    public void shiftedRotorShouldShiftLetterAndGetWiredLetterAndReturnShiftedBackLetter(
            int rotorPos, char letter, char expected) {
        rotor.setIndicator(rotorPos);
        final int actual = rotorService.cipherBackward(rotor, indexOf(letter));
        assertThat(letterOf(actual), is(expected));
    }

    @DataProvider
    public Object[][] shiftedRingDataProvider() {
        return new Object[][]{
                {0, 'A', 'T'},
                {25, 'B', 'F'},
                {24, 'C', 'N'},
                {23, 'D', 'P'},
                {22, 'E', 'M'},
                {21, 'F', 'P'},
                {20, 'G', 'P'},
                {19, 'H', 'S'},
                {18, 'I', 'Q'},
                {17, 'J', 'O'},
                {16, 'K', 'M'},
                {15, 'L', 'G'},
                {14, 'M', 'C'},
                {13, 'N', 'G'},
                {12, 'O', 'S'},
                {11, 'P', 'A'},
                {10, 'Q', 'C'},
                {9, 'R', 'Z'},
                {8, 'S', 'C'},
                {7, 'T', 'C'},
                {6, 'U', 'F'},
                {5, 'V', 'D'},
                {4, 'W', 'B'},
                {3, 'X', 'Z'},
                {2, 'Y', 'T'},
                {1, 'Z', 'P'},
        };
    }

    @Test(dataProvider = "shiftedRingDataProvider")
    public void shiftedRingShouldShiftLetterInOppositeDirection(
            int ringPos, char letter, char expected) {
        rotor.setRing(ringPos);
        final int actual = rotorService.cipherBackward(rotor, indexOf(letter));
        assertThat(letterOf(actual), is(expected));
    }
}
