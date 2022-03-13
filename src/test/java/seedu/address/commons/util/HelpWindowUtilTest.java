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
        HelpWindowUtil helpWindowUtil = new HelpWindowUtil(validLink);
        assertEquals(helpWindowUtil.convertToUrl(), URI.create("https://github.com/AY2122S2-CS2103-F11-2"));
    }

    @Test
    public void userGuide_checkInvalidLink_throwsIoException() throws IOException {
        HelpWindowUtil helpWindowUtil = new HelpWindowUtil(invalidLink);
        assertThrows(IllegalArgumentException.class, () -> helpWindowUtil.convertToUrl());
    }

    @Test
    public void userGuide_cannotOpenBrowser_throwsIoException() throws IOException {
        HelpWindowUtil helpWindowUtil = new HelpWindowUtil(invalidLink);
        assertThrows(IllegalArgumentException.class, () -> helpWindowUtil.goToUrl());
    }

    @Test
    public static void assertHelpWindowUtilSuccess() {
        HelpWindowUtil helpWindowUtil = new HelpWindowUtil(validLink);
        Object expectedModel = new HelpWindowUtil("https://github.com/AY2122S2-CS2103-F11-2");
        assertEquals(helpWindowUtil, expectedModel);
        assertEquals(helpWindowUtil.getUrl(), ((HelpWindowUtil) expectedModel).getUrl());
    }
}
