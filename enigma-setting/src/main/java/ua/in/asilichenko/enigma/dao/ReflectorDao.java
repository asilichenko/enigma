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

package ua.in.asilichenko.enigma.dao;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;

/**
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
