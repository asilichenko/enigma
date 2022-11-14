package ua.in.asilichenko.enigma.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.isOccupied;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.11.2022
 */
public class WhenPlugboardIsOccupied {

    private static final int[] plugboard = new int[]{0, 1, 2, 5, 4, 3};

    @DataProvider
    public Object[][] dataProvider() {
        return new Object[][]{
                {0, false},
                {1, false},
                {2, false},
                {3, true},
                {4, false},
                {5, true},
        };
    }

    @Test(dataProvider = "dataProvider")
    public void swappedLettersShouldBeOccupied(int letter, boolean expected) {
        assertThat(isOccupied(plugboard, letter), is(expected));
    }
}
