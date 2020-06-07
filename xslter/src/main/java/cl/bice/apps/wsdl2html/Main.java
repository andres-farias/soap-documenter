package cl.bice.apps.wsdl2html;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * This main class aims to provide a way to execute via command line, the XSTL converter to produce HTML from an WSLD document.
 * <p>
 * USE:
 * - the program receives three arguments.
 * 1. The WSDL file route
 * 2. The HTML output
 * 3. Options
 * - the only program option, so far, es --xslt [output.html]
 * <p>
 * EXAMPLES:
 * - java -jar wsdl2html --xslt anXSLTFile  myWSDL anOutputFile.html
 */
public class Main {

    /** The logger for this class */
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws TransformerException, FileNotFoundException {

        /* First think is to validate parameters */
        ArgumentParser argumentParser = createArgumentParser();
        Namespace namespace = validateArgs(argumentParser, args);

        /* Por si el namespace viene vacio */
        assert namespace != null;

        File xslt_file;
        TransformerXLST transformerXLST;
        if (namespace.get("xslt") != null) {
            xslt_file = namespace.get("xslt");
            transformerXLST = new TransformerXLST(xslt_file);
        }
        /* If no file was specified, the default contained XSLT file is used */
        else {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("wsdl-viewer.xsl");
            transformerXLST = new TransformerXLST(input);
        }

        /* The WSDL file is read as well */
        File xml2Transform = new File(namespace.getString("wsdl_file"));
        Source wsdlSource = new StreamSource(xml2Transform);

        File outputFile = new File("output.html");
        transformerXLST.transform(wsdlSource, outputFile);

        logger.info("File " + xml2Transform.getName() + " was successfully transformed and saved to " + outputFile.getAbsolutePath());
        System.exit(0);

    }

    /**
     * This method is responsible for creating the Argument Parser for this app. So far, the following arguments have been defined:
     * <ul>
     * <li>The XSLT file to produce the transformation.</li>
     * <li>The WSDL file to be converted to HTML.</li>
     * <li>The HTML file where the output will be directed.</li>
     * </ul>
     *
     * @return An argument parser created as this app requires it to be.
     */
    private static ArgumentParser createArgumentParser() {

        ArgumentParser argParser = ArgumentParsers.newFor("XLSTer").build().defaultHelp(true).description("anXSLTFile.xslt  myWsdl.wsdl anOutputFile.html");

        /* Special optional argument is defined to specify the transformation file */
        argParser.addArgument("-x", "--xslt").type(File.class).help("The XSLT file that will transforms an WSDL file");

        argParser.addArgument("wsdl_file").help("The WSDL file to be converted to HTML");
        argParser.addArgument("html_out_file").help("The HTML file where the output will be directed");

        return argParser;
    }

    /**
     * This method is responsible for verifying that every given arg is to be a file.
     */
    private static Namespace validateArgs(ArgumentParser argumentParser, String[] args) {

        /* The first validation is on the structure of the arguments, as expected */
        Namespace namespace;
        try {
            namespace = argumentParser.parseArgs(args);
        } catch (ArgumentParserException e) {
            argumentParser.handleError(e);
            System.exit(1);
            return null;
        }

        /* Validation of the XML file to be transformed */
        String wsdl_file = namespace.getString("wsdl_file");
        if (!new File(wsdl_file).exists()) {
            logger.error("The file " + wsdl_file + " was not found.");
            System.exit(0);
        }

        return namespace;
    }
}
