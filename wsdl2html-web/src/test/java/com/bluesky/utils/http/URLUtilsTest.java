package com.bluesky.utils.http;

import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * This class aims to test the URL utilities.
 */
public class URLUtilsTest {

    @org.junit.Test
    public void saveURLtoFileRigh() throws IOException, URISyntaxException {

        String url = "http://www.ooze.com/Ooze.1.text";

        File file = URLUtils.saveURLtoFile(url);

        /* It's asserted that the file exists */
        Assert.assertTrue(file.exists());
    }

    @org.junit.Test(expected = MalformedURLException.class)
    public void saveURLtoFileWrongProtocol() throws IOException, URISyntaxException {

        /* Wrong URL protocol */
        String url = "httx://www.ooze.com/Ooze.1.text";
        URLUtils.saveURLtoFile(url);
    }

    @org.junit.Test()
    public void createURL() throws IOException, URISyntaxException {

        /* Wrong URL protocol */
        URL url = URLUtils.createURL("http://www.ooze.com/Ooze.1.text");

        assertNotNull(url);
    }

    @org.junit.Test(expected = MalformedURLException.class)
    public void createURLWrong() throws IOException, URISyntaxException {

        /* Wrong URL protocol */
        URLUtils.createURL("httx://www.ooze.com/Ooze.1.text");
    }
}