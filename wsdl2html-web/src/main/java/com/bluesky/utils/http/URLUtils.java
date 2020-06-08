package com.bluesky.utils.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * This class aims to provide help for URL requirements.
 */
public class URLUtils {

    /** The logger for this class */
    private static final Logger logger = LogManager.getLogger(URLUtils.class);

    private static final String SAVED_FILE_PATH = "calculator.wsdl";

    /**
     * This method is responsible for retrieving the content of a an <code>encodedURL</code> and save it to a File.
     *
     * @param encodedURL The URL where the content to be retrieved is located, encoded as text. This encoded URL will be validated.
     * @return The full file's path.
     * @throws IOException        Thrown if there is a problem while reading from the <code>encodedURL</code> or when writing to the output file.
     * @throws URISyntaxException Thrown if the URL is not well formed.
     */
    public static File saveURLtoFile(String encodedURL) throws IOException, URISyntaxException {

        URL theURL = createURL(encodedURL);

        return saveURLtoFile(theURL);
    }

    /**
     * This method is responsible for retrieving the content of a an <code>encodedURL</code> and save it to a File.
     * <code>https://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java</code>
     *
     * @param url The URL where the content to be retrieved is located, encoded as text. This encoded URL will be validated.
     * @return A reference to the file in which the URL was saved to.
     * @throws URISyntaxException Thrown if the URL is not well formed.
     * @throws IOException        Thrown if there is a problem while reading from the <code>url</code> or when writing to the output file.
     */
    public static File saveURLtoFile(URL url) throws URISyntaxException, IOException {

        /* The URL is validated */
        url.toURI();

        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(SAVED_FILE_PATH);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();

        logger.info("URL's content retrieved and save at " + SAVED_FILE_PATH);
        return new File(SAVED_FILE_PATH);
    }

    /**
     * This method is responsible for creating an URL instance, validating whether is well formed and correct.
     *
     * @param encodedURL The encoded as text URL that is to be created and validated.
     * @return A fresh new URL object created frmo the <code>encodedURL</code>.
     * @throws MalformedURLException Thrown if the <code>encodedURL</code> is not well formed.
     * @throws URISyntaxException    Thrown if the URL is not well formed.
     */
    public static URL createURL(String encodedURL) throws MalformedURLException, URISyntaxException {

        /* The encoded URL is transformed to an URL representation */
        URL anURL = new URL(encodedURL);

        /* And the URL is validated on its syntax */
        anURL.toURI();

        return anURL;
    }
}
