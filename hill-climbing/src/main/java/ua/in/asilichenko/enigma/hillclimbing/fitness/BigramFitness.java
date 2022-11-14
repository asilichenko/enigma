package ua.in.asilichenko.enigma.hillclimbing.fitness;

public class BigramFitness extends UnigramFitness {

    private static final String FILE_NAME = "bigrams.txt";

    public BigramFitness() {
        super(2, FILE_NAME);
    }
}
