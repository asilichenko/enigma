package ua.in.asilichenko.enigma.util;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.RotorUtils.reverse;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 13.11.2022
 */
public class WhenRotorReverseWiring {

    private static final String WIRING_FORWARD = "BDFHJLCPRTXVZNYEIWGAKMUSQO"; // wiring III
    private static final String WIRING_BACKWARD = "TAGBPCSDQEUFVNZHYIXJWLRKOM";

    @Test
    public void mappingLettersAndMappedLettersShouldBeSwapped() {
        final int[] input = indexOf(WIRING_FORWARD);
        final int[] actual = reverse(input);
        assertThat(stringOf(actual), is(WIRING_BACKWARD));
    }
}
