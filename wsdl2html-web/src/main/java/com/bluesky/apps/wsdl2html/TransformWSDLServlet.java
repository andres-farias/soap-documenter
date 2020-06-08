package com.bluesky.apps.wsdl2html;

import cl.bice.apps.wsdl2html.TransformerXLST;
import com.bluesky.utils.files.FileUtils;
import com.bluesky.utils.http.QueryStringParser;
import com.bluesky.utils.http.URLUtils;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@WebServlet(
        name = "TransformFromFileServlet",
        description = "Transforms an WSDL file to HTML.",
        urlPatterns = "/transformFromFile")
public class TransformWSDLServlet extends HttpServlet {

    /** The logger for this class */
    private static final Logger logger = LogManager.getLogger(TransformWSDLServlet.class);

    private static final String OUTPUT_FILE_LOCATION = "./out/output.html";

    /**
     * The query string name that is to be used on the querystring section of the URL's requests
     */
    private static final String WSDL_QUERY_STRING_NAME = "wsdl";

    /**
     * This method is responsible for receiving an URL and consume the WSDL file and perform the transformation.
     * The name of the query string (the key) is defined in the global constant <code>WSDL_QUERY_STRING_NAME</code>.
     *
     * @param req  The HTTP request, including the URL as a query string parameter.
     * @param resp The WSDL documentation, as an HTML/txt document.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /* The WSDL's URL is obtained from the request */
        URL wsdlURL = obtainURLFromQueryString(req);

        /* The URL is used to retrieve the WSDL file (as a file) */
        File wsdl;
        try {
            wsdl = URLUtils.saveURLtoFile(wsdlURL);
        } catch (URISyntaxException uriException) {
            String errorMessage = "There was an error while retrieving the content of the WSDL from the given URL: " + wsdlURL.toString();
            logger.error(errorMessage, uriException);
            throw new ServletException(errorMessage, uriException);
        }

        /* The XSLT transformation is loaded into the Transformer instance */
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("wsdl-viewer.xsl");
        //FileUtils.writeFileToStream(input, System.out);
        TransformerXLST transformerXLST;

        try {
            //transformerXLST = new TransformerXLST(input);
            transformerXLST = new TransformerXLST();
        } catch (TransformerConfigurationException confException) {
            String errorMessage = "There was an error while processing the XSLT document.";
            logger.error(errorMessage, confException);
            throw new ServletException(errorMessage, confException);
        }

        /* The WSDL's HTML documentation is written to the HTTP response */
        File outputWSDLHTMLContent = new File(OUTPUT_FILE_LOCATION);

        /* Transforming to HTML documentation */
        try {
            transformerXLST.transform(new StreamSource(wsdl), outputWSDLHTMLContent);
        } catch (TransformerException transformationException) {
            String errorMessage = "There was an error while transforming the WSDL document.";
            logger.error(errorMessage, transformationException);
            throw new ServletException(errorMessage, transformationException);
        }

        /* Then it's written to the response "as is" */
        PrintWriter writer = resp.getWriter();
        FileUtils.writeToFile(outputWSDLHTMLContent, writer);

        logger.info("WSDL successfully generated!");
    }

    /**
     * This method is responsible for extract the query string parameter containing the WSDL's URL.
     *
     * @param req The request received.
     * @return The value, meaning the WSDL's URL, contained on the query string parameter.
     * @throws ServletException Thrown if there is no query string variable named as expected.
     */
    private URL obtainURLFromQueryString(HttpServletRequest req) throws ServletException {

        String queryString = req.getQueryString();
        QueryStringParser queryStringParser = new QueryStringParser(queryString);
        String wsdlQS = queryStringParser.getParameterValue(WSDL_QUERY_STRING_NAME);

        /* If there was no query string variable, an exception is thrown */
        if (wsdlQS == null) {
            String errorMessage = "The HTTP (GET) request you sent did not contain any <B>" + WSDL_QUERY_STRING_NAME + "</B> query string parameter and therefore, no WSDL could be processed. Please check the URL.";
            logger.error(errorMessage);
            throw new ServletException(errorMessage);
        }

        /* The query string variable was found, so the value is validated to be an URL */
        URL wsdlURL;
        try {
            wsdlURL = URLUtils.createURL(wsdlQS);
        } catch (MalformedURLException e) {
            String message = "The WSDL's URL provided was malformed. Please check the URL.";
            logger.error(message, e);
            throw new ServletException(message, e);
        } catch (URISyntaxException e) {
            String message = "The WSDL's URL syntax was invalid.Please check the URL.";
            logger.error(message, e);
            throw new ServletException(message);
        }

        logger.debug("WSDL URL found on query string: " + wsdlURL);
        return wsdlURL;
    }
}
