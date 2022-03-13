package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class HelpWindowUtilTest {

    private static String validLink = "https://github.com/AY2122S2-CS2103-F11-2";
    private static String invalidLink = "inValId  Link";

    @Test
    public void openUserGuide_validLink_throwsIoException() throws IOException {
        assertTrue(HelpWindowUtil.goToUrl(validLink));
    }

    @Test
    public void openUserGuide_invalidLink_throwsIoException() throws IOException {
        assertFalse(HelpWindowUtil.goToUrl(invalidLink));
    }
}
