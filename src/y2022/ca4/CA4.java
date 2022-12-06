package y2022.ca4;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;
import utilities.Printer;

public class CA4 {
    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            int incr = 0;
            for (String string : list) {
                String s1 = string.split(",")[0];
                String s2 = string.split(",")[1];
                int i11 = Integer.parseInt(s1.split("-")[0]);
                int i12 = Integer.parseInt(s1.split("-")[1]);
                int i21 = Integer.parseInt(s2.split("-")[0]);
                int i22 = Integer.parseInt(s2.split("-")[1]);
                Range range1 = new Range(i11, i12);
                Range range2 = new Range(i21, i22);
                if (range1.contains(range2) || range2.contains(range1)) {
                    incr++;
                }
            }
            Printer.println(incr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
