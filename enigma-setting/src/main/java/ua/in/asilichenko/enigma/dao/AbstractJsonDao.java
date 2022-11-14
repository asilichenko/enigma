package ua.in.asilichenko.enigma.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract DAO to load json property files.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 12.10.2022
 */
public abstract class AbstractJsonDao<T> {

    private static final String JSON_PATH_FORMAT = "json/%s.json";

    private final TypeReference<HashMap<String, T>> typeReference;
    private final String jsonPath;
    private final ObjectMapper mapper = new ObjectMapper();

    private Map<String, T> map = new ConcurrentHashMap<>();

    AbstractJsonDao(String jsonPath, TypeReference<HashMap<String, T>> typeReference) {
        this.typeReference = typeReference;
        this.jsonPath = String.format(JSON_PATH_FORMAT, jsonPath);
    }

    private void readToMap() {
        try {
            final URL resource = getClass().getClassLoader().getResource(jsonPath);

            if (null == resource) throw new IllegalStateException("Json file not found: " + jsonPath);

            map = mapper.readValue(resource, typeReference);
        } catch (IOException e) {
            throw new IllegalStateException("Json issue", e);
        }
    }

    public T forName(String name) {
        if (!map.containsKey(name)) readToMap();
        return map.get(name);
    }
}
