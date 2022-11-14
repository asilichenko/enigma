package ua.in.asilichenko.enigma.hillclimbing.fitness;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 25.10.2022
 */
public class UnigramFitness extends FitnessFunction {

    private static final String FILES_ROOT = "/de/";
    private static final String FILE_NAME = "unigrams.txt";

    private Map<Integer, Integer> map = new HashMap<>();

    private final int metric;

    private static int hashOf(int[] chars) {
        return Arrays.hashCode(chars);
    }

    private static int hashOf(char[] chars) {
        final int[] index = indexOf(chars);
        return hashOf(index);
    }

    private static int hashOf(String s) {
        return hashOf(s
                .replace('Ä', 'A')
                .replace('Ö', 'O')
                .replace('Ü', 'U')
                .replace('ß', 'S')
                .toCharArray());
    }

    public UnigramFitness() {
        this(1, FILE_NAME);
    }

    UnigramFitness(int metric, String FILE_NAME) {
        this.metric = metric;
        try (final InputStream is = this.getClass().getResourceAsStream(FILES_ROOT + FILE_NAME);
             final Reader r = new InputStreamReader(is, StandardCharsets.UTF_8);
             final Stream<String> lines = new BufferedReader(r).lines()) {

            lines.map(line -> line.split(" ")).forEach(line -> {
                final int key = hashOf(line[0]);
                // n-grams with umlauts are count as without
                map.compute(key, (k, v) -> (null == v ? 0 : v) + Integer.valueOf(line[1]));
            });

        } catch (Exception e) {
            throw new IllegalStateException(String.format("Failed to load %d-gram:", metric), e);
        }
    }

    private int[] window(int[] text, int pos) {
        final int[] retval = new int[metric];
        System.arraycopy(text, pos, retval, 0, metric);
        return retval;
    }

    @Override
    public final long score(int[] text) {
        long retval = 0;
        for (int i = 0; i < (text.length - metric); i++) {
            final int[] window = window(text, i);
            final int key = hashOf(window);
            retval += map.getOrDefault(key, 0);
        }
        return retval;
    }
}
