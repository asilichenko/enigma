package ua.in.asilichenko.enigma.service;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 29.10.2022
 */
public class WhenCreateBackwardWiring {

    private final RotorService rotorService = new RotorService();

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                // A -> E <> E -> A
                //ABCDEFGHIJKLMNOPQRSTUVWXYZ    ABCDEFGHIJKLMNOPQRSTUVWXYZ
                {"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "UWYGADFPVZBECKMTHXSLRINQOJ"}, // I
                {"AJDKSIRUXBLHWTMCQGZNPYFVOE", "AJPCZWRLFBDKOTYUQGENHXMIVS"}, // II
                {"BDFHJLCPRTXVZNYEIWGAKMUSQO", "TAGBPCSDQEUFVNZHYIXJWLRKOM"}, // III
        };
    }

    @Test(dataProvider = "getData")
    public void lettersMappingShouldBeReversed(String forwardWiring, String expected) {
        final int[] wiring = indexOf(forwardWiring);
        final int[] actual = rotorService.backwardWiringOf(wiring);
        assertThat(stringOf(actual), is(expected));
    }
}