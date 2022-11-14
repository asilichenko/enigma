package ua.in.asilichenko.enigma.hillclimbing.service;

import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.dao.WheelDao;
import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM3;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 12.11.2022
 */
@SuppressWarnings("unused")
public class HillClimbServiceM3 extends HillClimbService<EnigmaM3> {

    public HillClimbServiceM3() {
        final ComputeService<EnigmaM3> computeService = new ComputeService<>();
        computeService.setPlugboardService(new PlugboardService());

        final EnigmaServiceM3 enigmaService = new EnigmaServiceM3();
        enigmaService.setWheelDao(new WheelDao());
        enigmaService.setReflectorDao(new ReflectorDao());

        this.setComputeService(computeService);
        this.setEnigmaService(enigmaService);
    }
}
