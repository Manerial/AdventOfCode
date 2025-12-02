package utilities;

import utilities.errors.LocalSessionCreated;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ResourceIO {
    protected static final String RESOURCE_PATH = System.getProperty("user.dir") + "\\resources\\";
    public static final String DELIMITER = "\r\n";
    private static String localSession;

    private ResourceIO() {
    } // Necessary

    /**
     * Read a resource file and return its content
     *
     * @param filepath : the path to the file to read
     * @return the content of the file
     * @throws IOException when cannot read line
     */
    public static List<String> readListFromResourceFile(String filepath) throws IOException {
        InputStream inputStream = new FileInputStream(RESOURCE_PATH + filepath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return readBufferReader(bufferedReader);
    }

    /**
     * Read an url and return the content
     *
     * @param urlStr : the url to read
     * @return the content of the url
     * @throws IOException when cannot read the url or open the connexion
     */
    public static List<String> readListFromURL(String urlStr) throws IOException {
        URL url = URI.create(urlStr).toURL();
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Cookie", "session=" + localSession);
        InputStream inputStream = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return readBufferReader(bufferedReader);
    }

    /**
     * Read a buffer reader and return its content
     *
     * @param bufferedReader : the bufferReader to read
     * @return the content of the file
     * @throws IOException when cannot read line
     */
    public static List<String> readBufferReader(BufferedReader bufferedReader) throws IOException {
        List<String> stringList = new LinkedList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringList.add(line);
        }
        bufferedReader.close();
        return stringList;
    }

    public static void saveLocalSession() throws IOException {
        try {
            localSession = readListFromResourceFile("localSession").getFirst();
        } catch (IOException e) {
            writeInFile("localSession", "REGISTER YOUR AOC SESSION HERE");
            throw new LocalSessionCreated();
        }
    }

    public static void writeInFile(String fileName, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(RESOURCE_PATH + fileName, false);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.append(content);
        }
    }
}
