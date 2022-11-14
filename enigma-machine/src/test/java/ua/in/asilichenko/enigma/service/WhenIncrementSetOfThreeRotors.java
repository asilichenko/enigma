package ua.in.asilichenko.enigma.service;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.domain.Rotor;
import ua.in.asilichenko.enigma.domain.RotorSet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;
import static ua.in.asilichenko.enigma.util.LetterUtils.mod;

/**
 * <p>III - always
 * <p>II - when III at notch pos or itself at notch pos
 * <p>I - only when II at notch pos
 * <p>
 * Creation date: 06.10.2022
 */
public class WhenIncrementSetOfThreeRotors {

    private static final int FIRST_ROTOR = 0;
    private static final int SECOND_ROTOR = 1;
    private static final int THIRD_ROTOR = 2;

    private static final int FIRST_NOTCH = 7;
    private static final int SECOND_NOTCH = 25;
    private static final int THIRD_NOTCH = 11;

    private static final int NO_NOTCH_POS = 0;
    private static final int SECOND_POS_AT_NOTCH = 17;
    private static final int THIRD_POS_AT_NOTCH = 3;

    private static final int[] DUMMY_WIRING = new int[LETTERS_COUNT];
    private static final String DUMMY_NAME = "";

    private RotorSetService rotorSetService;

    @BeforeMethod
    public void setUp() {
        rotorSetService = new RotorSetService();
        rotorSetService.setRotorService(new RotorService());
    }

    private RotorSet createRotorSet() {
        return new RotorSet(null,
                Arrays.asList(
                        new Rotor(DUMMY_NAME, DUMMY_WIRING, DUMMY_WIRING, FIRST_NOTCH),
                        new Rotor(DUMMY_NAME, DUMMY_WIRING, DUMMY_WIRING, SECOND_NOTCH),
                        new Rotor(DUMMY_NAME, DUMMY_WIRING, DUMMY_WIRING, THIRD_NOTCH)
                )
        );
    }

    private RotorSet initData(int indicator1, int indicator2, int indicator3) {
        final RotorSet retval = createRotorSet();
        final List<Rotor> rotors = retval.getRotors();
        final Iterator<Rotor> iterator = rotors.iterator();
        iterator.next().setIndicator(indicator1);
        iterator.next().setIndicator(indicator2);
        iterator.next().setIndicator(indicator3);
        return retval;
    }

    private void execute(RotorSet rotorSet) {
        rotorSetService.increment(rotorSet);
    }

    private void test(RotorSet rotorSet, int rotorIndex, int rotorPos, boolean expected) {
        final List<Rotor> rotors = rotorSet.getRotors();
        assertThat(rotors.get(rotorIndex).getIndicator(), is(expected ? mod(rotorPos + 1) : rotorPos));
    }

    @DataProvider
    public Object[][] rotorPosDataProvider() {
        return new Object[][]{
                {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10},
                {11}, {12}, {13}, {14}, {15}, {16}, {17}, {18}, {19}, {20},
                {21}, {22}, {23}, {24}, {25}
        };
    }

    @Test(dataProvider = "rotorPosDataProvider")
    public void thirdShouldBeIncrementedAlways(int rotorPos) {
        final RotorSet rotorSet = initData(NO_NOTCH_POS, NO_NOTCH_POS, rotorPos);
        execute(rotorSet);
        test(rotorSet, THIRD_ROTOR, rotorPos, true);
    }

    @Test(dataProvider = "rotorPosDataProvider")
    public void thirdAtNotchPosSecondShouldBeIncremented(int rotorPos) {
        final RotorSet rotorSet = initData(NO_NOTCH_POS, rotorPos, THIRD_POS_AT_NOTCH);
        execute(rotorSet);
        test(rotorSet, SECOND_ROTOR, rotorPos, true);
    }

    @Test(dataProvider = "rotorPosDataProvider")
    public void secondAtNotchPosShouldBeIncremented(int rotorPos) {
        final RotorSet rotorSet = initData(NO_NOTCH_POS, rotorPos, NO_NOTCH_POS);
        execute(rotorSet);
        test(rotorSet, SECOND_ROTOR, rotorPos, SECOND_POS_AT_NOTCH == rotorPos);
    }

    @Test(dataProvider = "rotorPosDataProvider")
    public void secondAtNotchPosFirstShouldBeIncremented(int rotorPos) {
        final RotorSet rotorSet = initData(rotorPos, SECOND_POS_AT_NOTCH, 0);
        execute(rotorSet);
        test(rotorSet, FIRST_ROTOR, rotorPos, true);
    }

    @Test(dataProvider = "rotorPosDataProvider")
    public void secondNotAtNotchPosFirstShouldNotBeIncremented(int rotorPos) {
        final RotorSet rotorSet = initData(rotorPos, NO_NOTCH_POS, NO_NOTCH_POS);
        execute(rotorSet);
        test(rotorSet, FIRST_ROTOR, rotorPos, false);
    }
}
