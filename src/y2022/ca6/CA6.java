package y2022.ca6;

import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

public class CA6 {
    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                Printer.print("Solution 1 : ");
                findFirstPacket(item, 4);
                Printer.print("Solution 2 : ");
                findFirstPacket(item, 14);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void findFirstPacket(String item, int size) {
        int incr = 0;
        boolean found = false;
        while (!found && incr < item.length() - size) {
            String subStr = item.substring(incr, incr + size);
            long count = subStr.chars().distinct().count();
            if (count == size) {
                Printer.println(incr + size);
                found = true;
            }
            incr++;
        }
    }
}
