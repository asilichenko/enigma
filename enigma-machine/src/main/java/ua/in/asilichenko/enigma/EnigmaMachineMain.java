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

package ua.in.asilichenko.enigma;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.in.asilichenko.enigma.dao.ReflectorDao;
import ua.in.asilichenko.enigma.dao.WheelDao;
import ua.in.asilichenko.enigma.domain.EnigmaMachine;
import ua.in.asilichenko.enigma.service.*;

import java.time.LocalTime;

/**
 * Enigma machine simulator.
 * <p>
 * Creation date: 05.10.2022
 */
public class EnigmaMachineMain {

    private static final Logger logger = LoggerFactory.getLogger(EnigmaMachineMain.class);

    private static final EnigmaMachineService enigmaMachineService;

    static {
        final RotorService rotorService = new RotorService();
        rotorService.setWheelDao(new WheelDao());

        final ReflectorService reflectorService = new ReflectorService();
        reflectorService.setReflectorDao(new ReflectorDao());

        final RotorSetService rotorSetService = new RotorSetService();
        rotorSetService.setRotorService(rotorService);
        rotorSetService.setReflectorService(reflectorService);

        final PlugboardService plugboardService = new PlugboardService();

        final EnigmaSettingService enigmaSettingService = new EnigmaSettingService();

        enigmaMachineService = new EnigmaMachineService();
        enigmaMachineService.setRotorService(rotorService);
        enigmaMachineService.setRotorSetService(rotorSetService);
        enigmaMachineService.setPlugboardService(plugboardService);
        enigmaMachineService.setEnigmaSettingService(enigmaSettingService);
    }

    public static void main(String[] args) {
        logger.info(LocalTime.now() + " > Start.");
        final Stopwatch stopwatch = Stopwatch.createStarted();

        final String setting = "UKW-C, III II VIII, PFT, 0 10 7, CQ DU EN FR GX IS JP KO TY VZ";
        final String text = "VJTLFJXWQFLTDIJNHFPTPVJDLRTXECCKBDSARJKVAMAA";

        final EnigmaMachine enigmaMachine = enigmaMachineService.build(setting);
        final String result = enigmaMachineService.cipher(enigmaMachine, text);
        logger.info(result);

        logger.info(LocalTime.now() + " > Finished. Elapsed: " + stopwatch.elapsed());
    }
}
