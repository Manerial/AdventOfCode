package template;

import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

public class AOC0 implements AOC {
    @Override
    public void run(String file) {
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
