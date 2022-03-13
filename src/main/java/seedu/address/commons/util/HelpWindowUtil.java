package seedu.address.commons.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class HelpWindowUtil {

    private String url;

    public HelpWindowUtil(String url) {
        this.url = url;
    }

    /**
     * Converts string into URI, and opens in User's default web browser.
     *
     * @return boolean if website is successfully opened.
     * @throws IOException when there is an error opening the website.
     */
    public boolean goToUrl() throws IOException {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        URI newLink = convertToUrl();

        try {
            desktop.browse(newLink);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Converts string to the URI class.
     *
     * @return URI of the new link.
     */
    public URI convertToUrl() {
        return URI.create(this.url);
    }

    public String getUrl() {
        return url;
    }
}
