package utilities;

import template.AOC;

public class AOCFactory {
    private static String packageName = "adventofcode.";

    public static AOC getAOC(int year, int day) {
        try {
            String z = (day < 10) ? "0" : "";
            String subPackageName = "aoc_" + year + "_" + z + day + ".";
            String className = "AOC_" + year + "_" + z + day;
            String factoredClassName = packageName + subPackageName + className;
            Class classRef = Class.forName(factoredClassName);
            return (AOC) classRef.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
