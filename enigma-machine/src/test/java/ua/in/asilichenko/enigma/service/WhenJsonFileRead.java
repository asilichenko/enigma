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
