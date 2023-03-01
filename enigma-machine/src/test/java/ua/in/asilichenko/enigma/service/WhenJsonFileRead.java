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

package ua.in.asilichenko.enigma.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.entity.Wheel;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Creation date: 12.10.2022
 */
public class WhenJsonFileRead {

    private <T> Map<String, T> read(String fileName) throws IOException {
        final URL resource = getClass().getClassLoader().getResource(String.format("json/%s.json", fileName));

        if (null == resource) throw new IllegalStateException("Json file not found");

        return new ObjectMapper().readValue(resource, new TypeReference<HashMap<String, T>>() {
        });
    }

    @Test
    public void wheelsShouldBeMapped() throws IOException {
        final Map<String, Wheel> map = read("wheels");

        assertThat(map.isEmpty(), is(false));

        assertThat(map.get("I"), notNullValue());
        assertThat(map.get("II"), notNullValue());
        assertThat(map.get("III"), notNullValue());
        assertThat(map.get("IV"), notNullValue());
        assertThat(map.get("V"), notNullValue());
        assertThat(map.get("VIII"), notNullValue());
    }

    @Test
    public void reflectorsShouldBeMapped() throws IOException {
        final Map<String, String> map = read("reflectors");

        assertThat(map.isEmpty(), is(false));

        assertThat(map.get("UKW-B"), notNullValue());
        assertThat(map.get("UKW-C"), notNullValue());
    }
}
