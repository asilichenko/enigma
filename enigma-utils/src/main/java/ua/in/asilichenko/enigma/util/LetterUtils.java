package ua.in.asilichenko.enigma.util;

import java.nio.CharBuffer;

/**
 * Utils to work with letters.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 06.10.2022
 */
public class LetterUtils {
    // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
    // A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z

    /**
     * Number of letters on each wheel.
     */
    public static final int LETTERS_COUNT = 26;

    /**
     * Alphabet.
     * <p>
     * Each letter is placed according to alphabetical order.
     */
    public static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Index of letter 'A'.
     */
    private static final int INDEX_OF_LETTER_A = 'A';

    /**
     * Modulo function.
     *
     * @param index could be negative
     * @return modulo
     */
    public static int mod(int index) {
        return (LETTERS_COUNT + index) % LETTERS_COUNT;
    }

    /**
     * Convert character array to corresponding int array,
     * where each element is an index of corresponding char (letter code minus code of 'A').
     *
     * @param text char array
     * @return int array
     */
    public static int[] indexOf(char[] text) {
        final int[] retval = new int[text.length];
        for (int i = 0; i < retval.length; i++) retval[i] = indexOf(text[i]);
        return retval;
    }

    /**
     * Convert char to index relative to 'A'
     *
     * @param letter char
     * @return index relative to 'A'
     */
    public static int indexOf(char letter) {
        return letter - INDEX_OF_LETTER_A;
    }

    /**
     * Convert String to array of int, where each element is an index of a letter from the input text.
     *
     * @param text text
     * @return array of letter indexes
     */
    public static int[] indexOf(String text) {
        return indexOf(text.toCharArray());
    }

    /**
     * Convert position of letter relative to position of 'A' to correspond letter.
     * If index is out of bound means greater then position of 'Z' then it is modulo
     * <p>
     * <p>index 0 relative to 'A' is 'A' then returns 'A'
     * <p>index 25 relative to 'A' is 'Z' then returns 'Z'
     * <p>index 26 relative to 'A' is out of bound, 26 cycled in 'A'-'Z' is 0, 0 relative to 'A' is 'A' then returns 'A'
     *
     * @param index position of letter relative to 'A': 'A' = 0, etc.
     * @return letter cycled in 'A'-'Z'
     */
    public static char letterOf(int index) {
        return (char) (INDEX_OF_LETTER_A + mod(index));
    }

    /**
     * Convert array of letter indexes back to readable uppercase String text.
     *
     * @param text array of letter indexes
     * @return String text
     */
    public static String stringOf(int[] text) {
        final CharBuffer charBuffer = CharBuffer.allocate(text.length);
        for (int letter : text) charBuffer.append(letterOf(letter));
        return charBuffer.rewind().toString();
    }
}
