package adventofcode.y2021.ca2;

import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

public class CA2 {

    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                Printer.println(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
