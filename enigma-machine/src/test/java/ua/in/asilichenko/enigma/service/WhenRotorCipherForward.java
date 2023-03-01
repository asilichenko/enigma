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
 * Creation date: 11.10.2022
 */
public class WhenRotorCipherForward {

    private static final int DUMMY_NOTCH = -1;

    private static final String WIRING_FORWARD = "BDFHJLCPRTXVZNYEIWGAKMUSQO"; // wiring III
    private static final String WIRING_BACKWARD = "TAGBPCSDQEUFVNZHYIXJWLRKOM";

    private Rotor rotor;
    private RotorService rotorService;

    @BeforeMethod
    public void setUp() {
        rotorService = spy(new RotorService());
        rotor = spy(new Rotor("", indexOf(WIRING_FORWARD), indexOf(WIRING_BACKWARD), DUMMY_NOTCH));
    }

    @DataProvider
    public Object[][] notShiftedRotorDataProvider() {
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // BDFHJLCPRTXVZNYEIWGAKMUSQO
        return new Object[][]{
                {'A', 'B'},
                {'B', 'D'},
                {'C', 'F'},
                {'D', 'H'},
                {'E', 'J'},
                {'F', 'L'},
                {'G', 'C'},
                {'H', 'P'},
                {'I', 'R'},
                {'J', 'T'},
                {'K', 'X'},
                {'L', 'V'},
                {'M', 'Z'},
                {'N', 'N'},
                {'O', 'Y'},
                {'P', 'E'},
                {'Q', 'I'},
                {'R', 'W'},
                {'S', 'G'},
                {'T', 'A'},
                {'U', 'K'},
                {'V', 'M'},
                {'W', 'U'},
                {'X', 'S'},
                {'Y', 'Q'},
                {'Z', 'O'},
        };
    }

    @Test(dataProvider = "notShiftedRotorDataProvider")
    public void notShiftedRotorShouldReturnLetterAccordingToWiring(char letter, char expected) {
        final int actual = rotorService.cipherForward(rotor, indexOf(letter));
        assertThat(letterOf(actual), is(expected));
    }

    @DataProvider
    public Object[][] shiftedRotorDataProvider() {
        // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
        // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z
        // B  D  F  H  J  L  C  P  R  T  X  V  Z  N  Y  E  I  W  G  A  K  M  U  S  Q  O
        return new Object[][]{
                {0, 'A', 'B'},  //  0 + A( 0) =  0 mod =  0(A) -> B( 1) -  0 =   1 mod =  1(B)
                {1, 'B', 'E'},  //  1 + B( 1) =  2 mod =  2(C) -> F( 5) -  1 =   4 mod =  4(E)
                {2, 'C', 'H'},  //  2 + C( 2) =  4 mod =  4(E) -> J( 9) -  2 =   7 mod =  7(H)
                {3, 'D', 'Z'},  //  3 + D( 3) =  6 mod =  6(G) -> C( 2) -  3 =  -1 mod = 25(Z)
                {4, 'E', 'N'},  //  4 + E( 4) =  8 mod =  8(I) -> R(17) -  4 =  13 mod = 13(N)
                {5, 'F', 'S'},  //  5 + F( 5) = 10 mod = 10(K) -> X(23) -  5 =  18 mod = 18(S)
                {6, 'G', 'T'},  //  6 + G( 6) = 12 mod = 12(M) -> Z(25) -  6 =  19 mod = 19(T)
                {7, 'H', 'R'},  //  7 + H( 7) = 14 mod = 14(O) -> Y(24) -  7 =  17 mod = 17(R)
                {8, 'I', 'A'},  //  8 + I( 8) = 16 mod = 16(Q) -> I( 8) -  8 =   0 mod =  0(A)
                {9, 'J', 'X'},  //  9 + J( 9) = 18 mod = 18(S) -> G( 6) -  9 =  -3 mod = 23(X)
                {10, 'K', 'A'}, // 10 + K(10) = 20 mod = 20(U) -> K(10) - 10 =   0 mod =  0(A)
                {11, 'L', 'J'}, // 11 + L(11) = 22 mod = 22(W) -> U(20) - 11 =   9 mod =  9(J)
                {12, 'M', 'E'}, // 12 + M(12) = 24 mod = 24(Y) -> Q(16) - 12 =   4 mod =  4(E)
                {13, 'N', 'O'}, // 13 + N(13) = 26 mod =  0(A) -> B( 1) - 13 = -12 mod = 14(O)
                {14, 'O', 'R'}, // 14 + O(14) = 28 mod =  2(C) -> F( 5) - 14 =  -9 mod = 17(R)
                {15, 'P', 'U'}, // 15 + P(15) = 30 mod =  4(E) -> J( 9) - 15 =  -6 mod = 20(U)
                {16, 'Q', 'M'}, // 16 + Q(16) = 32 mod =  6(G) -> C( 2) - 16 = -14 mod = 12(M)
                {17, 'R', 'A'}, // 17 + R(17) = 34 mod =  8(I) -> R(17) - 17 =   0 mod =  0(A)
                {18, 'S', 'F'}, // 18 + S(18) = 36 mod = 10(K) -> X(23) - 18 =   5 mod =  5(F)
                {19, 'T', 'G'}, // 19 + T(19) = 38 mod = 12(M) -> Z(25) - 19 =   6 mod =  6(G)
                {20, 'U', 'E'}, // 20 + U(20) = 40 mod = 14(O) -> Y(24) - 20 =   4 mod =  4(E)
                {21, 'V', 'N'}, // 21 + V(21) = 42 mod = 16(Q) -> I( 8) - 21 = -13 mod = 13(N)
                {22, 'W', 'K'}, // 22 + W(22) = 44 mod = 18(S) -> G( 6) - 22 = -16 mod = 10(K)
                {23, 'X', 'N'}, // 23 + X(23) = 46 mod = 20(U) -> K(10) - 23 = -13 mod = 13(N)
                {24, 'Y', 'W'}, // 24 + Y(24) = 48 mod = 22(W) -> U(20) - 24 =  -4 mod = 22(W)
                {25, 'Z', 'R'}, // 25 + Z(25) = 50 mod = 24(Y) -> Q(16) - 25 =  -9 mod = 17(R)
        };
    }

    @Test(dataProvider = "shiftedRotorDataProvider")
    public void shiftedRotorShouldShiftLetterAndGetWiredLetterAndReturnShiftedBackLetter(
            int indicator, char letter, char expected) {
        rotor.setIndicator(indicator);
        final int actual = rotorService.cipherForward(rotor, indexOf(letter));
        assertThat(letterOf(actual), is(expected));
    }
}