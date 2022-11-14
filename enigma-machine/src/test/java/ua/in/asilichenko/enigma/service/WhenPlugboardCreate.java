package ua.in.asilichenko.enigma.service;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.in.asilichenko.enigma.domain.Plugboard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.enigma.util.LetterUtils.stringOf;

/**
 * Creation date: 12.10.2022
 */
public class WhenPlugboardCreate {

    private final PlugboardService plugboardService = new PlugboardService();

    @DataProvider
    public Object[][] plugsDataProvider() {
        return new Object[][]{
                {new String[]{"PO", "ML", "IU", "KJ", "NH", "YT", "GB", "VF", "RE", "DC"},
                        "AGDCRVBNUKJMLHPOQESYIFWXTZ"},
        };
    }

    @Test(dataProvider = "plugsDataProvider")
    public void lettersShouldBeSwapped(String[] plugs, String expected) {
        final Plugboard plugboard = plugboardService.createPlugboard(plugs);
        final String actual = stringOf(plugboard.getLayout());
        assertThat(actual, is(expected));
    }
}
