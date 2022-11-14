package ua.in.asilichenko.enigma.hillclimbing.service;

import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM3;
import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM4;
import ua.in.asilichenko.enigma.hillclimbing.entity.Rotor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 12.11.2022
 */
public class WhenPreCompute {

    private static final int[] REFLECTOR_B = indexOf("YRUHQSLDPXNGOKMIEBFZCWVJAT");
    private static final int[] REFLECTOR_B_THIN = indexOf("ENKQAUYWJICOPBLMDXZVFTHRGS");

    private static final String ROTOR_BETA = "LEYJVCNIXWPBQMDRTAKZGFUHOS";
    private static final String ROTOR_III = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
    private static final String ROTOR_II = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
    private static final String ROTOR_VIII = "FKQHTLXOCBJSPDZRAMEWNIUYGV";

    private static final int[] DUMMY_NOTCH = new int[0];
    private static final int[] NOTCH_II = new int[]{12};
    private static final int[] NOTCH_VIII = new int[]{7, 20};

    @Mock
    private ComputeService<EnigmaM3> computeServiceM3;

    @Mock
    private ComputeService<EnigmaM4> computeServiceM4;

    @BeforeClass
    public void setUp() {
        openMocks(this);
        when(computeServiceM3.precompute(any(EnigmaM3.class), anyInt())).thenCallRealMethod();
        when(computeServiceM4.precompute(any(EnigmaM4.class), anyInt())).thenCallRealMethod();
    }

    private Rotor createRotor(String wiring, int[] notches, int ring, int indicator) {
        return new Rotor(wiring, notches, ring, indicator);
    }

    @Test
    public void enigmaM3ShouldBePrecomputed() {
        final EnigmaM3 enigma = new EnigmaM3(REFLECTOR_B,
                createRotor(ROTOR_III, DUMMY_NOTCH, 0, indexOf('P')),
                createRotor(ROTOR_II, NOTCH_II, 10, indexOf('F')),
                createRotor(ROTOR_VIII, NOTCH_VIII, 7, indexOf('T'))
        );
        final int[][] expected = new int[][]{
                indexOf("XWHJTULCZDOGPSKMRQNEFYBAVI"), // alphabet at first step
                indexOf("RFUKNBJIHGDVYEWSZAPXCLOTMQ"), // alphabet at second step
        };

        final int[][] actual = computeServiceM3.precompute(enigma, expected.length);
        assertThat(actual, is(expected));
    }

    @Test
    public void enigmaM4ShouldBePrecomputed() {
        final EnigmaM4 enigma = new EnigmaM4(REFLECTOR_B_THIN,
                createRotor(ROTOR_BETA, DUMMY_NOTCH, 0, indexOf('Q')),
                createRotor(ROTOR_III, DUMMY_NOTCH, 0, indexOf('P')),
                createRotor(ROTOR_II, NOTCH_II, 10, indexOf('F')),
                createRotor(ROTOR_VIII, NOTCH_VIII, 7, indexOf('T'))
        );
        final int[][] expected = new int[][]{
                indexOf("PCBTSILMFXVGHUYAZWEDNKRJOQ"), // alphabet at first step
                indexOf("ZEXGBTDOUVYNWLHSRQPFIJMCKA"), // alphabet at second step
        };

        final int[][] actual = computeServiceM4.precompute(enigma, expected.length);
        assertThat(actual, is(expected));
    }
}