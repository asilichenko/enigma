package ua.in.asilichenko.enigma.hillclimbing.fitness;

/**
 * Creation date: 27.10.2022
 */
public abstract class FitnessFunction {

    public abstract long score(int[] text);
}