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

import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM3;
import ua.in.asilichenko.enigma.hillclimbing.entity.Rotor;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creation date: 03.11.2022
 */
public class EnigmaServiceM3 extends AbstractEnigmaService<EnigmaM3> {

    @Override
    List<Rotor> rotorsForNames(List<String> rotorNames) {
        return rotorNames.stream().map(this::rotorForName).collect(Collectors.toList());
    }

    @Override
    public EnigmaM3 build(int[] reflector, List<Rotor> samples, int ringMid, int ringRight) {
        final Iterator<Rotor> iterator = samples.iterator();
        return new EnigmaM3(reflector,
                new Rotor(iterator.next()),
                new Rotor(iterator.next(), ringMid),
                new Rotor(iterator.next(), ringRight)
        );
    }

    @Override
    EnigmaM3 build(int[] reflector, List<Rotor> samples, int ringMid, int ringRight, int[] plugboard) {
        final Iterator<Rotor> iterator = samples.iterator();
        final Rotor leftRotor = new Rotor(iterator.next());
        final Rotor midRotor = new Rotor(iterator.next(), ringMid);
        final Rotor rightRotor = new Rotor(iterator.next(), ringRight);
        return new EnigmaM3(reflector, leftRotor, midRotor, rightRotor, plugboard);
    }
}
