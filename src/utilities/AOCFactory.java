package utilities;

import template.AOC;

import java.lang.reflect.InvocationTargetException;

public class AOCFactory {
    private AOCFactory() {
    }

    public static AOC getAOC(int year, int day) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String z = (day < 10) ? "0" : "";
        String subPackageName = "aoc" + year + ".day" + z + day + ".";
        String className = "AOCRunner";
        String packageName = "adventofcode.";
        String factoredClassName = packageName + subPackageName + className;
        Class<?> classRef = Class.forName(factoredClassName);
        return (AOC) classRef.getDeclaredConstructor().newInstance();
    }
}
