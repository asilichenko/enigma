/*
 *   Copyright (c) 2022 Oleksii Sylichenko.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
