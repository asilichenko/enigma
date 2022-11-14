package ua.in.asilichenko.enigma.util;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.connect;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 10.11.2022
 */
public class WhenPlugboardConnect {

    @Test
    public void shouldBeSwappedConnectedLetters() {
        final int[] plugboard = new int[]{0, 1, 2, 3, 4, 5};
        final int[] expected = new int[]{1, 0, 2, 3, 4, 5};
        connect(plugboard, 0, 1);
        assertThat(plugboard, is(expected));
    }
}
