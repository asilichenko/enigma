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

import ua.in.asilichenko.enigma.dao.AbstractJsonDao;
import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.dao.WheelDao;
import ua.in.asilichenko.enigma.entity.Wheel;
import ua.in.asilichenko.enigma.hillclimbing.entity.AbstractEnigma;
import ua.in.asilichenko.enigma.hillclimbing.entity.Rotor;

import java.util.List;

import static ua.in.asilichenko.enigma.util.LetterUtils.indexOf;

/**
 * Creation date: 12.11.2022
 */
public abstract class AbstractEnigmaService<E extends AbstractEnigma> {

    private static final String REFLECTOR_NOT_FOUND_MSG = "Reflector not found: ";
    private static final String WHEEL_NOT_FOUND_MSG = "Wheel not found: ";

    WheelDao wheelDao;
    private ReflectorDao reflectorDao;

    public void setWheelDao(WheelDao wheelDao) {
        this.wheelDao = wheelDao;
    }

    public void setReflectorDao(ReflectorDao reflectorDao) {
        this.reflectorDao = reflectorDao;
    }

    public int[] reflectorForName(String name) {
        final String reflector = reflectorDao.forName(name);
        if (null == reflector) throw new IllegalArgumentException(REFLECTOR_NOT_FOUND_MSG + name);
        return indexOf(reflector);
    }

    Rotor rotorOf(AbstractJsonDao<Wheel> dao, String name, int ring, int indicator) {
        final Wheel wheel = dao.forName(name);
        if (null == wheel) throw new IllegalArgumentException(WHEEL_NOT_FOUND_MSG + name);
        return new Rotor(wheel.getWiring(), wheel.getNotches(), ring, indicator);
    }

    Rotor rotorForName(String name) {
        final Wheel wheel = wheelDao.forName(name);
        return new Rotor(wheel.getWiring(), wheel.getNotches());
    }

    abstract List<Rotor> rotorsForNames(List<String> rotorNames);

    abstract E build(int[] reflector, List<Rotor> rotors, int ringMid, int ringRight);

    abstract E build(int[] reflector, List<Rotor> rotors, int ringMid, int ringRight, int[] plugboard);

    public int[] cipher(int[] text,
                        String reflectorName,
                        List<String> rotorNames,
                        int ringMid, int ringRight,
                        int[] indicators,
                        int[] plugboard) {

        final int[] reflector = reflectorForName(reflectorName);
        final List<Rotor> rotors = rotorsForNames(rotorNames);

        final E enigma = build(reflector, rotors, ringMid, ringRight, plugboard);
        enigma.adjustIndicators(indicators);
        return enigma.cipher(text);
    }
}
