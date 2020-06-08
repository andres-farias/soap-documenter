package cl.bice.apps.wsdl2html;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;

/**
 * This class is responsible for providing the means to instantiate the transformer.
 */
public class TransformerXLST {

    /** The logger for this class */
    private static final Logger logger = LogManager.getLogger(TransformerXLST.class);

    private Transformer transformer;

    /**
     * This empty constructor initialize the instance with the included xls file for the transformation.
     *
     * @throws TransformerConfigurationException Thrown if there is a problem with the transformation file.
     */
    public TransformerXLST() throws TransformerConfigurationException {
        this(Thread.currentThread().getContextClassLoader().getResourceAsStream("wsdl-viewer.xsl"));
    }

    /**
     * This constructor is responsible for instantiating this transformer using an xslt file located at the given path. For this, a specialized constructor is used.
     *
     * @param xslt_file_path The path where the XSLT file is located.
     * @throws FileNotFoundException Thrown when the given file is not found.
     */
    public TransformerXLST(String xslt_file_path) throws FileNotFoundException, TransformerConfigurationException {
        this(new File(xslt_file_path));
    }

    /**
     * This constructor is responsible for instantiating this transformer using a given stream info.
     *
     * @param xsltInputStream The input stream from where the XSLT is read.
     * @throws TransformerConfigurationException Thrown if the XSLT file is not valid for transforming anything.
     */
    public TransformerXLST(InputStream xsltInputStream) throws TransformerConfigurationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        this.transformer = factory.newTransformer(new StreamSource(xsltInputStream));
        logger.info("XLST Transformer was successfully read and validated.");
    }

    /**
     * This constructor is responsible for instantiating this transformer using an xslt file located at the given path.
     * For this, a specialized constructor is used.
     *
     * @param xsltFile The file containing the XSLT.
     * @throws FileNotFoundException             Thrown when the given file is not found.
     * @throws TransformerConfigurationException Thrown if the XSLT file is not valid for transforming anything.
     */
    TransformerXLST(File xsltFile) throws FileNotFoundException, TransformerConfigurationException {
        this(new FileInputStream(xsltFile));
        logger.info("XLST Transformer was successfully created from file: " + xsltFile.getName());
    }

    /**
     * This method is responsible to provide a facade from the original transform method, but receiving the file.
     *
     * @param wsdlSource The WSDL that is to be transformed.
     * @param outputFile The output file where the result is to be written.
     * @throws TransformerException Throw if the XSTL could not make the transformation.
     */
    public void transform(Source wsdlSource, File outputFile) throws TransformerException {
        this.transformer.transform(wsdlSource, new StreamResult(outputFile));
    }

    /**
     * This method is responsible to provide a facade from the original transform method, receiving an URL.
     *
     * @param wsdlURL    The WSDL that is to be transformed.
     * @param outputFile The output file where the result is to be written.
     * @throws TransformerException Throw if the XSTL could not make the transformation.
     * @throws IOException          Thrown if there is a problem connecting to the URL's content.
     */
    void transform(URL wsdlURL, File outputFile) throws TransformerException, IOException {

        /* The URL is read and a stream is created to it */
        StreamSource xmlSource = new StreamSource(new InputStreamReader(wsdlURL.openStream()));

        /* The transformation is made with the base method */
        this.transformer.transform(xmlSource, new StreamResult(outputFile));
    }

    /**
     * This method is responsible for performing the transformation directly to a writer.
     *
     * @param wsdlURL     The WSDL that is to be transformed.
     * @param printStream The stream to where the transformation is written.
     * @throws TransformerException Throw if the XSTL could not make the transformation.
     * @throws IOException          Thrown if there is a problem connecting to the URL's content.
     */
    public void transform(URL wsdlURL, Writer printStream) throws TransformerException, IOException {

        /* The URL is read and a stream is created to it */
        StreamSource xmlSource = new StreamSource(new InputStreamReader(wsdlURL.openStream()));

        /* The transformation is made with the base method */
        this.transformer.transform(xmlSource, new StreamResult(printStream));
    }

    public void transform(StreamSource wsdlSource, PrintWriter printStream) throws TransformerException {
        this.transformer.transform(wsdlSource, new StreamResult(printStream));
    }
}
