package ua.in.asilichenko.enigma.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.10.2022
 */
public class WhenPlugboardReleasePlugPair {

    @DataProvider
    public Object[][] dataProvider() {
        return new Object[][]{
                {
                        new int[]{0, 2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25},
                        1, 2,
                        new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25}
                },
                {
                        new int[]{0, 2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25},
                        3, 4,
                        new int[]{0, 2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25}
                },
                {
                        new int[]{0, 2, 1, 4, 3, 6, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25},
                        5, 6,
                        new int[]{0, 2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25}
                },
        };
    }

    @Test(dataProvider = "dataProvider")
    public void releasedLettersShouldBeReturnedToTheirPlaces(int[] plugboard, int plugA, int plugB, int[] expected) {
        PlugboardUtils.release(plugboard, plugA, plugB);
        assertThat(plugboard, is(expected));
    }
}