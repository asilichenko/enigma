package ua.in.asilichenko.enigma.service;

import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.domain.Reflector;

import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 28.10.2022
 */
public class ReflectorService {

    private static final String VALIDATION_MSG = "Incorrect reflector wiring: %s. Length must be: %d, but was: %d.";

    private ReflectorDao reflectorDao;

    public void setReflectorDao(ReflectorDao reflectorDao) {
        this.reflectorDao = reflectorDao;
    }

    public Reflector reflectorByName(String reflectorName) {
        final String wiring = reflectorDao.forName(reflectorName);
        validateReflector(wiring);
        return new Reflector(reflectorName, indexOf(wiring));
    }

    private void validateReflector(String wiring) {
        if (LETTERS_COUNT != wiring.length()) {
            throw new IllegalStateException(String.format(VALIDATION_MSG, wiring, LETTERS_COUNT, wiring.length()));
        }
    }
}
