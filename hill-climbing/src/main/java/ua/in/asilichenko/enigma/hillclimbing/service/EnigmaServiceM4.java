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

import ua.in.asilichenko.enigma.dao.WheelExtraDao;
import ua.in.asilichenko.enigma.entity.Wheel;
import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM4;
import ua.in.asilichenko.enigma.hillclimbing.entity.Rotor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Creation date: 11.11.2022
 */
public class EnigmaServiceM4 extends AbstractEnigmaService<EnigmaM4> {

    private WheelExtraDao wheelExtraDao;

    public void setWheelExtraDao(WheelExtraDao wheelExtraDao) {
        this.wheelExtraDao = wheelExtraDao;
    }

    private Rotor rotorExtraForName(String name) {
        final Wheel wheel = wheelExtraDao.forName(name);
        return new Rotor(wheel.getWiring(), wheel.getNotches());
    }

    @Override
    List<Rotor> rotorsForNames(List<String> rotorNames) {
        final Iterator<String> iterator = rotorNames.iterator();
        return Arrays.asList(
                rotorExtraForName(iterator.next()),
                rotorForName(iterator.next()),
                rotorForName(iterator.next()),
                rotorForName(iterator.next())
        );
    }

    @Override
    EnigmaM4 build(int[] reflector, List<Rotor> rotors, int ringMid, int ringRight) {
        final Iterator<Rotor> iterator = rotors.iterator();
        return new EnigmaM4(reflector,
                new Rotor(iterator.next()),
                new Rotor(iterator.next()),
                new Rotor(iterator.next(), ringMid),
                new Rotor(iterator.next(), ringRight));
    }

    @Override
    EnigmaM4 build(int[] reflector, List<Rotor> rotors, int ringMid, int ringRight, int[] plugboard) {
        final Iterator<Rotor> iterator = rotors.iterator();
        return new EnigmaM4(reflector,
                new Rotor(iterator.next()),
                new Rotor(iterator.next()),
                new Rotor(iterator.next(), ringMid),
                new Rotor(iterator.next(), ringRight),
                plugboard);
    }
}
