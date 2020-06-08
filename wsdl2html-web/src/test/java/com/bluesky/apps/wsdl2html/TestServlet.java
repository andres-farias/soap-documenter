package com.bluesky.apps.wsdl2html;

import cl.bice.apps.wsdl2html.TransformerXLST;
import cl.bice.utils.files.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(
        name = "TestServlet",
        description = "Tests de API for transforming.",
        urlPatterns = "/test")
public class TestServlet extends HttpServlet {

    /**
     * The logger for this class
     */
    private static final Logger logger = LogManager.getLogger(TestServlet.class);

    private static final String WSDL_FILE_LOCATION = "salesforce-cliente-creacion.wsdl";
    private static final String OUTPUT_FILE_LOCATION = "./out/output.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("wsdl-viewer.xsl");
        TransformerXLST transformerXLST;
        try {
            transformerXLST = new TransformerXLST(input);
        } catch (TransformerConfigurationException e) {
            logger.error(e);
            throw new ServletException(e);
        }
        logger.debug("Transformer: " + transformerXLST);

        StreamSource wsdl_stream = new StreamSource(new FileReader(WSDL_FILE_LOCATION));
        File outputFile = new File(OUTPUT_FILE_LOCATION);

        try {
            transformerXLST.transform(wsdl_stream, outputFile);
        } catch (TransformerException e) {
            logger.error(e);
            throw new ServletException(e);
        }

        FileUtils.writeToFile(outputFile, resp.getWriter());
    }
}
