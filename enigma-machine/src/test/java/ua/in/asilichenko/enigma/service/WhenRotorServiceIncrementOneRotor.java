package ua.in.asilichenko.enigma.service;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.domain.Rotor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;

/**
 * Creation date: 06.10.2022
 */
public class WhenRotorServiceIncrementOneRotor {

    private static final int LAST_ROTOR_POS = LETTERS_COUNT - 1;

    private final RotorService rotorService = new RotorService();

    private Rotor rotor;

    @BeforeMethod
    public void setUp() {
        rotor = Mockito.mock(Rotor.class);
        doCallRealMethod().when(rotor).setIndicator(anyInt());
        when(rotor.getIndicator()).thenCallRealMethod();
    }

    @DataProvider
    public Object[][] rotorPosDataProvider() {
        return new Object[][]{
                {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9},
                {10}, {11}, {12}, {13}, {14}, {15}, {16}, {17}, {18}, {19},
                {20}, {21}, {22}, {23}, {24}
        };
    }

    @Test(dataProvider = "rotorPosDataProvider")
    public void rotorPosShouldBeIncremented(int rotorPos) {
        rotor.setIndicator(rotorPos);
        rotorService.incrementIndicator(rotor);
        assertThat(rotor.getIndicator(), is(rotorPos + 1));
    }

    @Test
    public void lastRotorPosShouldBeZero() {
        rotor.setIndicator(LAST_ROTOR_POS);
        rotorService.incrementIndicator(rotor);
        assertThat(rotor.getIndicator(), is(0));
    }
}
