package ua.in.asilichenko.enigma.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.letterOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.11.2022
 */
public class WhenLetterOfLetterIndex {
    // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
    // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z

    @DataProvider
    public Object[][] lettersDataProvider() {
        return new Object[][]{
                {0, 'A'}, {1, 'B'}, {2, 'C'}, {3, 'D'}, {4, 'E'},
                {5, 'F'}, {6, 'G'}, {7, 'H'}, {8, 'I'}, {9, 'J'},
                {10, 'K'}, {11, 'L'}, {12, 'M'}, {13, 'N'}, {14, 'O'},
                {15, 'P'}, {16, 'Q'}, {17, 'R'}, {18, 'S'}, {19, 'T'},
                {20, 'U'}, {21, 'V'}, {22, 'W'}, {23, 'X'}, {24, 'Y'},
                {25, 'Z'},
        };
    }

    @DataProvider
    public Object[][] overflowDataProvider() {
        return new Object[][]{
                {26, 'A'}, {27, 'B'}, {28, 'C'}, {29, 'D'}, {30, 'E'},
                {31, 'F'}, {32, 'G'}, {33, 'H'}, {34, 'I'}, {35, 'J'},
                {36, 'K'}, {37, 'L'}, {38, 'M'}, {39, 'N'}, {40, 'O'},
                {41, 'P'}, {42, 'Q'}, {43, 'R'}, {44, 'S'}, {45, 'T'},
                {46, 'U'}, {47, 'V'}, {48, 'W'}, {49, 'X'}, {50, 'Y'},
                {51, 'Z'},
        };
    }

    @DataProvider
    public Object[][] negativeDataProvider() {
        return new Object[][]{
                {-26, 'A'}, {-25, 'B'}, {-24, 'C'}, {-23, 'D'}, {-22, 'E'},
                {-21, 'F'}, {-20, 'G'}, {-19, 'H'}, {-18, 'I'}, {-17, 'J'},
                {-16, 'K'}, {-15, 'L'}, {-14, 'M'}, {-13, 'N'}, {-12, 'O'},
                {-11, 'P'}, {-10, 'Q'}, {-9, 'R'}, {-8, 'S'}, {-7, 'T'},
                {-6, 'U'}, {-5, 'V'}, {-4, 'W'}, {-3, 'X'}, {-2, 'Y'},
                {-1, 'Z'},
        };
    }

    @Test(dataProvider = "lettersDataProvider")
    public void letterIndexShouldBeConverted(int in, char expected) {
        assertThat(letterOf(in), is(expected));
    }

    @Test(dataProvider = "overflowDataProvider")
    public void overflowIndexShouldBeTakenModulo(int in, char expected) {
        assertThat(letterOf(in), is(expected));
    }

    @Test(dataProvider = "negativeDataProvider")
    public void negativeIndexShouldBeTakenModuloFromTheEnd(int in, char expected) {
        assertThat(letterOf(in), is(expected));
    }
}
