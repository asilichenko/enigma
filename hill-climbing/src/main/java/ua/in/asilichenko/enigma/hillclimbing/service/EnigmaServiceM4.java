package ua.in.asilichenko.enigma.hillclimbing.service;

import ua.in.asilichenko.enigma.dao.WheelExtraDao;
import ua.in.asilichenko.enigma.entity.Wheel;
import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM4;
import ua.in.asilichenko.enigma.hillclimbing.entity.Rotor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
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
