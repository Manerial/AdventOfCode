package y2022;

import y2022.ca1.CA1;
import y2022.ca2.CA2;
import y2022.ca3.CA3;
import y2022.ca4.CA4;
import y2022.ca5.CA5;
import y2022.ca6.CA6;

public class Launcher2022 {
    private static String year = "2022";
    public static void main(String[] args) {
        System.out.println("Solution jour 1");
        CA1.run(year + "/ca1.txt");
        System.out.println("Solution jour 2");
        CA2.run(year + "/ca2.txt");
        System.out.println("Solution jour 3");
        CA3.run(year + "/ca3.txt");
        System.out.println("Solution jour 4");
        CA4.run(year + "/ca4.txt");
        System.out.println("Solution jour 5");
        CA5.run(year + "/ca5_2.txt");
        System.out.println("Solution jour 6");
        CA6.run(year + "/ca6.txt");
    }
}
