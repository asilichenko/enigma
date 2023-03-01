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

package ua.in.asilichenko.enigma.service;

import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.domain.Reflector;

import static ua.in.asilichenko.enigma.util.LetterUtils.LETTERS_COUNT;
import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
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
