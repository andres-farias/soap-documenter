package com.bluesky.tests;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class TransformerFactoryTest {

    /**
     * The logger for this class
     */
    private static final Logger logger = LogManager.getLogger(TransformerFactoryTest.class);

    private static final String WSDL_FILE_LOCATION = "salesforce-cliente-creacion.wsdl";
    private static final String OUTPUT_FILE_LOCATION = "./out/output.html";

    @Test
    public void test1() throws TransformerConfigurationException, IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream xsltInputStream = classLoader.getResourceAsStream("wsdl-viewer.xsl");
        //writeFileToStream(xsltInputStream, System.out);
        logger.info("XSTL={}", xsltInputStream);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.newTransformer(new StreamSource(xsltInputStream));
        logger.info("Transformer={}", transformerFactory);
    }

    public static void writeFileToStream(InputStream input, PrintStream out) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            out.println(inputLine);
        }

    }

}
