package ua.in.asilichenko.enigma.hillclimbing.service;

import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.hillclimbing.service.ProcessedPercentCounter.permutationCount;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 04.11.2022
 */
public class WhenComputeProcessedPercent {

    private static final int ROTORS_IN_USE = 3;
    private static final float EPSILON = 0.01f;

    /**
     * Expected: Reflectors * TotalRotors! / (TotalRotors - UsedRotors)!
     */
    @DataProvider
    public Object[][] permutationDataProvider() {
        return new Object[][]{
                {1, 3, 6},
                {2, 3, 12},
                {1, 4, 24},
                {2, 4, 48},
                {1, 5, 60},
                {2, 5, 120},
                {1, 6, 120},
                {2, 6, 240},
                {1, 7, 210},
                {2, 7, 420},
                {1, 8, 336},
                {2, 8, 672},
        };
    }

    @Test(dataProvider = "permutationDataProvider")
    public void shouldBeReflectorsMulTotalFactorialDivTotalMinusThreeFactorial(int reflectors, int rotorsCnt, int expected) {
        assertThat(permutationCount(reflectors, rotorsCnt, ROTORS_IN_USE), is(expected));
    }

    @DataProvider
    public Object[][] percentageDataProvider() {
        return new Object[][]{
                {1, 3, new float[]{0.00f, 16.67f, 33.33f, 50.00f, 66.67f, 83.33f, 100.00f}},
                {2, 3, new float[]
                        {0.00f, 8.33f, 16.67f, 25.00f, 33.33f, 41.67f, 50.00f,
                                58.33f, 66.67f, 75.00f, 83.33f, 91.67f, 100.00f}
                },
        };
    }

    @Test(dataProvider = "percentageDataProvider")
    public void percentageShouldBeCountDivTotalCount(int reflectors, int rotorsCnt, float[] expectedArray) {
        final ProcessedPercentCounter counter = new ProcessedPercentCounter(reflectors, rotorsCnt, ROTORS_IN_USE);
        for (float expected : expectedArray) {
            final float actual = counter.next() - expected;
            assertThat(Math.abs(actual), Matchers.lessThan(EPSILON));
        }
    }
}