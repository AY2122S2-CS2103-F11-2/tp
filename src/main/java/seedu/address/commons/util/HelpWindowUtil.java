package seedu.address.commons.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class HelpWindowUtil {

    /**
     * Converts string into URI, and opens in User's default web browser.
     *
     * @param url the website to access.
     * @return boolean if website is successfully opened.
     * @throws IOException when there is an error opening the website.
     */
    public static boolean goToUrl(String url) throws IOException {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        try {
            desktop.browse(URI.create(url));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
