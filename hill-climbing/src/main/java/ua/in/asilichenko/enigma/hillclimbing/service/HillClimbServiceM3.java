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

import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.dao.WheelDao;
import ua.in.asilichenko.enigma.hillclimbing.entity.EnigmaM3;

/**
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
