package ua.in.asilichenko.enigma.hillclimbing.fitness;

public class TrigramFitness extends UnigramFitness {

    private static final String FILE_NAME = "trigrams.txt";

    public TrigramFitness() {
        super(3, FILE_NAME);
    }
}
