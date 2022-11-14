package ua.in.asilichenko.enigma.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import ua.in.asilichenko.enigma.entity.Wheel;

import java.util.HashMap;

/**
 * DAO for extra wheels.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 11.11.2022
 */
public class WheelExtraDao extends AbstractJsonDao<Wheel> {

    private static final String JSON_FILE_NAME = "wheels-extra";

    public WheelExtraDao() {
        super(JSON_FILE_NAME, new TypeReference<HashMap<String, Wheel>>() {
        });
    }
}
