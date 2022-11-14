package ua.in.asilichenko.enigma.dao;

/**
 * DAO for Thin reflectors.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 11.11.2022
 */
public class ReflectorThinDao extends ReflectorDao {

    private static final String JSON_FILE_NAME = "reflectors-thin";

    public ReflectorThinDao() {
        super(JSON_FILE_NAME);
    }
}
