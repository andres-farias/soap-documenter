package com.bluesky.utils.files;

import java.io.*;

public class FileUtils {

    /**
     * This method is responsible for writing the content of a file onto a destiny Writer.
     *
     * @param source  The source file to be sent to a Writer.
     * @param destiny The destiny writer.
     * @throws IOException Thrown if the source file cannot be found or if can't be closed.
     */
    public static void writeToFile(File source, PrintWriter destiny) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(source));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            destiny.append(inputLine);
        }
        in.close();
    }

    public static void writeFileToStream(InputStream input, PrintStream out) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            out.println(inputLine);
        }

    }

}
