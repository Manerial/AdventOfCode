package utilities;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileLoader {
    private static final String RESOURCE_PATH = System.getProperty("user.dir") + "\\resources\\";

    private FileLoader() {
    }

    /**
     * Read a file and return the content
     *
     * @param filePath : the path to the file to read
     * @return the content of the file
     * @throws IOException when cannot read line
     */
    public static List<String> readListFromFile(String filePath) throws IOException {
        List<String> stringList = new LinkedList<>();
        BufferedReader br = getBufferReader(filePath);
        String line;
        while ((line = br.readLine()) != null) {
            stringList.add(line);
        }
        br.close();
        return stringList;
    }

    /**
     * Return a BufferedReader to read a file
     *
     * @param fileName : the file name to read
     * @return a BufferedReader with the path of the file to read
     * @throws FileNotFoundException : All the File Not Found exceptions
     */
    private static BufferedReader getBufferReader(String fileName) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(RESOURCE_PATH + fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }
}
