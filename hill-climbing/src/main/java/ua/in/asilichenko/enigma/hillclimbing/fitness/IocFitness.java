package ua.in.asilichenko.enigma.hillclimbing.fitness;

import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;

public class IocFitness extends FitnessFunction {

    @Override
    public long score(int[] text) {
        final int[] histogram = new int[LETTERS_COUNT];
        for (int letter : text) histogram[letter]++;
        long retval = 0;
        for (int n : histogram) retval += n * n;
        return retval;
    }
}
