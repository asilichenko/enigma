package ua.in.asilichenko.enigma.hillclimbing;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM3;
import ua.in.asilichenko.enigma.hillclimbing.entity.Rotor;
import ua.in.asilichenko.enigma.hillclimbing.result.SearchResult;
import ua.in.asilichenko.enigma.hillclimbing.service.HillClimbService;
import ua.in.asilichenko.enigma.hillclimbing.service.HillClimbServiceM3;
import ua.in.asilichenko.enigma.hillclimbing.service.ProcessedPercentCounter;
import ua.in.asilichenko.enigma.service.EnigmaSettingService;
import ua.in.asilichenko.enigma.setting.EnigmaSetting;
import ua.in.asilichenko.enigma.setting.IndicatorsSetting;
import ua.in.asilichenko.enigma.setting.RingsSetting;
import ua.in.asilichenko.enigma.setting.RotorsSetting;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static ua.in.asilichenko.enigma.hillclimbing.config.GeneralConfig.IOC_FITNESS;
import static ua.in.asilichenko.enigma.hillclimbing.config.GeneralConfig.ROTORS_IN_USE_NUMBER;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.asString;
import static ua.in.asilichenko.enigma.util.PlugboardUtils.plugboardWith;

/**
 * Searching for Enigma machine setting of enciphered message by Hill-Climbing algorithm.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
public class HillClimbMain {

    private static final Logger logger = LoggerFactory.getLogger(HillClimbMain.class);

    private static final String PROCESSED_PERCENT_FORMAT = "%s > Processed: %.2f%%";
    private static final String RESULTS_PRINT_FORMAT = "%d: %s %s %s %s";

    private static final List<String> ALL_REFLECTORS = Arrays.asList("UKW-B", "UKW-C");
    private static final List<String> ALL_ROTORS = Arrays.asList("I", "II", "III", "IV", "V", "VI", "VII", "VIII");

    private static HillClimbService<EnigmaM3> hillClimbService = new HillClimbServiceM3();

    public static void main(String[] args) {
        logger.info(LocalTime.now() + " > Start");
        final Stopwatch stopwatch = Stopwatch.createStarted();

        // for testing use message same length as enciphered message
        // noinspection unused
        final int[] plain = indexOf("VONGRUPPEWESTANARMEEGRUPPEJBJXXWERZULEBZEITGUTAUFERDENWIRDNACHDEMTODEINENGELWERDENDENBLICKGENHIMMELFRAGSTDUDANNWARUMMANSIENICHTSEHENKANNERSTYWENNDIEWOLKENSCHLAFENGEHENKANNMANUNSAMHIMMELSEHENWIRHABENANGSTUNDSINDALLEINGOTTWEISSYICHWILLKEINENGELSEINSIELEBENHINTERMSONNENSCHEINGETRENNTVONUNSUNENDLICHWEITSIEMUESSENSICHANSTERNEKRALLENKKGANZFESTKKDAMITSIENICHTVOMHIMMELFALLEN");
        // noinspection unused
        final int[] enciphered = indexOf("JJFWOFYZWPSUUGOISZALBIDWFNQSXYRAITUPSLNFITRUBLDYCLJVODVHCLJUHFXVNNMUSCUABMHVMNUJFZEYBJKTFCIGYEFTUAYSPPJRLYRGYOFYJFHYWIMHQLODVFGJCORHYEVIBHTNIBFRMEZAQKHPSOONLMFHSTYUUXAAVFMETIDZWRIJYAJYXJKLGFJHMRDXXRFGYPRMDEDAIADTZMWKDUHNGPEKEEAKFOGJSCOFUQXTXQNKZTIPZHQJZPKTQDFYEBPFAKGLRWYRHYWMGVAWZGWFAZAWSLDERNTKPOHUQBUSGPJMFVTNEEQACLFPYQASIHAIROGFZSXUYGXGHFRIWWXMMQFIOYHRFCLOSJVDXIHNL");
        // noinspection unused
        final int[] text = indexOf(""); // enciphered text

        // Stage 1
        calculateThresholds(plain);

        // Stage 2
//        searchForOneRotorSet(enciphered);

        // Stage 3
        /*final int ringStep2 = 2;
        final int ringStep3 = 2;
        final long filterThreshold = 5_700;
        final long candidateThreshold = 9_800;
        final int printResultsN = 2;
        searchForAllRotors(text, ringStep2, ringStep3, filterThreshold, candidateThreshold, printResultsN);*/

        logger.info(LocalTime.now() + " > Finish. Total elapsed: " + stopwatch.elapsed());
    }

    @SuppressWarnings("unused")
    private static void calculateThresholds(int[] plain) {
        // "UKW-C III II VIII  PFT  0 10 7  CQ DU EN FR GX IS JP KO TY VZ"
        final int[] encipher = cipher(plain, "UKW-C, III II VIII, PFT, 0 10 7, CQ DU EN FR GX IS JP KO TY VZ");
        logger.info("\nTest message enciphered:");
        logger.info(stringOf(encipher));

        final int[] decipherWithoutPlugboard = cipher(encipher, "UKW-C, III II VIII, PFT, 0 10 7");
        logger.info("\nScore of deciphered without plugboard, could be used to determine filter threshold:");
        logger.info(IOC_FITNESS.score(decipherWithoutPlugboard) + "");

        logger.info("\nMessage score, could be used to determine candidate threshold:");
        logger.info(IOC_FITNESS.score(plain) + "\n");
    }

    @SuppressWarnings("unused")
    private static void searchForOneRotorSet(int[] text) {
        final RotorsSetting rotorsSetting = new RotorsSetting("UKW-C", "III", "II", "VIII");

        final int ringMidStep = 2, ringRightStep = 2;
        final int filterThreshold = 5_700; // a bit lower than test result
        final int candidateThreshold = 9_800; // a bit lower than test result
        final int resultsToPrintN = 2;

        final Stopwatch stopwatch = Stopwatch.createStarted();

        final Queue<SearchResult> results = hillClimbService.search(
                text, rotorsSetting, ringMidStep, ringRightStep, filterThreshold, candidateThreshold
        );

        logger.info(LocalTime.now() + " > Elapsed: " + stopwatch.elapsed(TimeUnit.SECONDS) + "s");

        printResults(results, resultsToPrintN, text);
    }

    @SuppressWarnings("unused")
    private static int[] cipher(int[] text, String stringSetting) {
        final EnigmaSettingService settingService = new EnigmaSettingService();
        final EnigmaSetting setting = settingService.parse(stringSetting);
        return hillClimbService.cipher(text,
                setting.getReflector(),
                setting.getRotors(),
                setting.getRings()[setting.getRings().length - Rotor.MID],
                setting.getRings()[setting.getRings().length - Rotor.RIGHT],
                setting.getIndicators(),
                plugboardWith(setting.getPlugPairs())
        );
    }

    @SuppressWarnings("unused")
    private static void searchForAllRotors(int[] text, int ringStep2, int ringStep3, long filterThreshold, long candidateThreshold, int resultsPrintN) {
        final ProcessedPercentCounter percentCounter = new ProcessedPercentCounter(
                ALL_REFLECTORS.size(),
                ALL_ROTORS.size(),
                ROTORS_IN_USE_NUMBER
        );

        ALL_REFLECTORS.forEach(reflector -> {//
            ALL_ROTORS.forEach(rotor1 -> {//
                ALL_ROTORS.stream().filter(r -> !r.equals(rotor1)).forEach(rotor2 -> {//
                    ALL_ROTORS.stream().filter(r -> !r.equals(rotor1) && !r.equals(rotor2)).forEach(rotor3 -> {
                        //
                        logger.info(String.format(PROCESSED_PERCENT_FORMAT, LocalTime.now(), percentCounter.next()));

                        final Queue<SearchResult> results = hillClimbService.search(text,
                                new RotorsSetting(reflector, rotor1, rotor2, rotor3),
                                ringStep2,
                                ringStep3,
                                filterThreshold,
                                candidateThreshold);
                        printResults(results, resultsPrintN, text);
                        //
                    });//rotor3
                });//rotor2
            });//rotor1
        });//reflector
    }

    private static synchronized void printResults(Queue<SearchResult> results, int n, int[] text) {
        if (0 == n) return;

        logger.info("");
        IntStream.range(0, Math.min(n, results.size())).forEach(i -> {
            final SearchResult searchResult = results.poll();

            final String setting = String.format(RESULTS_PRINT_FORMAT,
                    searchResult.getScore(),
                    searchResult.getRotorsSetting(),
                    searchResult.getRingsSetting(),
                    searchResult.getIndicatorsSetting(),
                    asString(searchResult.getPlugboard()));

            final String deciphered = decipher(text, searchResult);

            logger.info(setting);
            logger.info(deciphered);
        });
        logger.info("");
    }

    private static String decipher(int[] text, SearchResult searchResult) {
        final IndicatorsSetting indicators = searchResult.getIndicatorsSetting();
        final RingsSetting rings = searchResult.getRingsSetting();
        final RotorsSetting rotors = searchResult.getRotorsSetting();

        final int[] decrypted = hillClimbService.cipher(text,
                rotors.getReflector(), rotors.getRotors(),
                rings.getRingMid(), rings.getRingRight(),
                indicators.getIndicators(),
                searchResult.getPlugboard()
        );

        return stringOf(decrypted);
    }
}
