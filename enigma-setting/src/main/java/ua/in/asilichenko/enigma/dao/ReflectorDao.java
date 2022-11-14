package ua.in.asilichenko.enigma.dao;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;

/**
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 11.10.2022
 */
public class ReflectorDao extends AbstractJsonDao<String> {

    private static final String JSON_FILE_NAME = "reflectors";

    ReflectorDao(String jsonFileName) {
        super(jsonFileName, new TypeReference<HashMap<String, String>>() {
        });
    }

    public ReflectorDao() {
        this(JSON_FILE_NAME);
    }
}
