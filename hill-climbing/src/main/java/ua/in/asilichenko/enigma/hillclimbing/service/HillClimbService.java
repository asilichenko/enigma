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

package ua.in.asilichenko.enigma.hillclimbing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.in.asilichenko.enigma.setting.RotorsSetting;
import ua.in.asilichenko.enigma.setting.IndicatorsSetting;
import ua.in.asilichenko.enigma.setting.RingsSetting;
import ua.in.asilichenko.enigma.hillclimbing.concurrency.ThreadService;
import ua.in.asilichenko.enigma.hillclimbing.result.PlugboardResult;
import ua.in.asilichenko.enigma.hillclimbing.result.SearchResult;
import ua.in.asilichenko.enigma.hillclimbing.entity.AbstractEnigma;
import ua.in.asilichenko.enigma.hillclimbing.entity.Rotor;

import java.time.LocalTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

import static ua.in.asilichenko.enigma.hillclimbing.utils.SearchResultUtils.asString;
import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;

/**
 * Creation date: 27.10.2022
 */
public class HillClimbService<E extends AbstractEnigma> {

    private static final Logger logger = LoggerFactory.getLogger(HillClimbService.class);

    /**
     * LETTERS_COUNT power of 2
     */
    @SuppressWarnings("WeakerAccess")
    static final int LETTERS_COUNT_2 = LETTERS_COUNT * LETTERS_COUNT;

    private AbstractEnigmaService<E> enigmaService;
    private ComputeService<E> computeService;

    void setEnigmaService(AbstractEnigmaService<E> enigmaService) {
        this.enigmaService = enigmaService;
    }

    void setComputeService(ComputeService<E> computeService) {
        this.computeService = computeService;
    }

    // could be overridden
    @SuppressWarnings("WeakerAccess")
    int countOfRingIteration(int ringStep2, int ringStep3) {
        return LETTERS_COUNT_2 / (ringStep2 * ringStep3);
    }

    // could be overridden
    @SuppressWarnings("WeakerAccess")
    void iterateRings(int ringStep2, int ringStep3, Consumer<RingsSetting> consumer) {
        for (int ring2 = 0; ring2 < LETTERS_COUNT; ring2 += ringStep2) {
            for (int ring3 = 0; ring3 < LETTERS_COUNT; ring3 += ringStep3) {
                consumer.accept(new RingsSetting(ring2, ring3));
            }
        }
    }

    // could be overridden
    @SuppressWarnings("WeakerAccess")
    void iterateIndicators(Consumer<IndicatorsSetting> consumer) {
        for (int indicator1 = 0; indicator1 < LETTERS_COUNT; indicator1++) {
            for (int indicator2 = 0; indicator2 < LETTERS_COUNT; indicator2++) {
                for (int indicator3 = 0; indicator3 < LETTERS_COUNT; indicator3++) {
                    consumer.accept(new IndicatorsSetting(indicator1, indicator2, indicator3));
                }
            }
        }
    }

    public Queue<SearchResult> search(int[] text, RotorsSetting rotorsSetting,
                                      int ringStep2, int ringStep3,
                                      long filterThreshold, long candidateThreshold) {
        logger.info(LocalTime.now() + " > Processing: " + rotorsSetting);

        final Queue<SearchResult> retval = new PriorityBlockingQueue<>();
        final ThreadService threadService = new ThreadService(countOfRingIteration(ringStep2, ringStep3));

        final int[] reflector = enigmaService.reflectorForName(rotorsSetting.getReflector());
        final List<Rotor> rotors = enigmaService.rotorsForNames(rotorsSetting.getRotors());

        iterateRings(ringStep2, ringStep3, ringSetting -> {
            threadService.submit(() -> {
                //
                final E enigma = enigmaService.build(
                        reflector, rotors, ringSetting.getRingMid(), ringSetting.getRingRight()
                );
                //
                iterateIndicators(indicatorsSetting -> {
                    //
                    enigma.adjustIndicators(indicatorsSetting.getIndicators());

                    final PlugboardResult result = computeService.compute(text, enigma, indicatorsSetting, filterThreshold);

                    if (null != result) {
                        final SearchResult searchResult = new SearchResult(
                                rotorsSetting,
                                ringSetting,
                                indicatorsSetting,
                                result.getPlugboard(),
                                result.getScore()
                        );
                        retval.add(searchResult);
                        candidateCheck(searchResult, candidateThreshold);
                    }
                    //
                });//indicators
                threadService.countDown();
            });//executorService
        });//rings

        threadService.await();

        return retval;
    }

    private void candidateCheck(SearchResult result, long candidateThreshold) {
        final long score = result.getScore();
        if (score < candidateThreshold) return;
        logger.warn("Found candidate [" + score + "]: " + asString(result));
    }

    public int[] cipher(int[] text,
                        String reflectorName,
                        List<String> rotorNames,
                        int ringMid, int ringRight,
                        int[] indicators,
                        int[] plugboard) {
        return enigmaService.cipher(text, reflectorName, rotorNames, ringMid, ringRight, indicators, plugboard);
    }
}
