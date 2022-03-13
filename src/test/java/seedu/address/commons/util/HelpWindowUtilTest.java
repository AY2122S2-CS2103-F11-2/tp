package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Test;

class HelpWindowUtilTest {

    private static String validLink = "https://github.com/AY2122S2-CS2103-F11-2";
    private static String invalidLink = "invalid link";

    @Test
    public void userGuide_checkLink_throwsIoException() throws IOException {
        assertEquals(HelpWindowUtil.convertToUrl(validLink), URI.create("https://github.com/AY2122S2-CS2103-F11-2"));
    }

    @Test
    public void userGuide_checkInvalidLink_throwsIoException() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> HelpWindowUtil.convertToUrl(invalidLink));
    }

}
