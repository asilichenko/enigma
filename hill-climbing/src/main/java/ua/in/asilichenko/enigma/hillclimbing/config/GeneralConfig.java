package ua.in.asilichenko.enigma.hillclimbing.config;

import ua.in.asilichenko.enigma.hillclimbing.fitness.*;

/**
 * Creation date: 04.11.2022
 */
public interface GeneralConfig {

    int ROTORS_IN_USE_NUMBER = 3;
    int PLUG_PAIRS_IN_USE = 10;

    @SuppressWarnings("unused")
    IocFitness IOC_FITNESS = new IocFitness();
    @SuppressWarnings("unused")
    UnigramFitness UNIGRAM_FITNESS = new UnigramFitness();
    @SuppressWarnings("unused")
    BigramFitness BIGRAM_FITNESS = new BigramFitness();
    @SuppressWarnings("unused")
    TrigramFitness TRIGRAM_FITNESS = new TrigramFitness();
    @SuppressWarnings("unused")
    TetragramFitness TETRAGRAM_FITNESS = new TetragramFitness();

    FitnessFunction PLUGBOARD_FIRST_STAGE_FITNESS = IOC_FITNESS;
    FitnessFunction PLUGBOARD_MID_STAGE_FITNESS = IOC_FITNESS;
    FitnessFunction PLUGBOARD_LAST_STAGE_FITNESS = IOC_FITNESS;

}
