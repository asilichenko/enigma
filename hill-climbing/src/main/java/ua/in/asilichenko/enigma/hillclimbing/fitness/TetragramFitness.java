package ua.in.asilichenko.enigma.hillclimbing.fitness;

public class TetragramFitness extends UnigramFitness {

    private static final String FILE_NAME = "tetragrams.txt";

    public TetragramFitness() {
        super(4, FILE_NAME);
    }
}
