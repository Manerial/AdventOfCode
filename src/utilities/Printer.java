package utilities;

public class Printer {

    private Printer() {
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static void println() {
        System.out.println();
    }

    public static void printerr(String message) {
        System.err.println(message);
    }
}
