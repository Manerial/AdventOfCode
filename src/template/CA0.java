package template;

import utilities.FileLoader;

import java.io.IOException;
import java.util.List;

public class CA0 {

    public static void run(String file) {
        // TODO Auto-generated method stub
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
