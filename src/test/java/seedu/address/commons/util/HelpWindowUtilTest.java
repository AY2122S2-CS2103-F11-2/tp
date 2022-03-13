package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Test;

class HelpWindowUtilTest {

    private static String validLink = "https://github.com/AY2122S2-CS2103-F11-2";

    @Test
    public void userGuide_checkLink_throwsIoException() throws IOException {
        assertEquals(HelpWindowUtil.convertToUrl(validLink), URI.create("https://github.com/AY2122S2-CS2103-F11-2"));
    }
}
