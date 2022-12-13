package utilities;

import template.AOC;

import java.lang.reflect.InvocationTargetException;

public class AOCFactory {
    private AOCFactory() {
    }

    /**
     * <pre>
     *     Using a day and a year, return AOC implementation to use.
     *     Example : getAOC(2022, 01) will return the AOC implementation of the AdventOfCode of 2022/12/01
     * </pre>
     *
     * @param year : the year of the AdventOfCode puzzle
     * @param day  : the day of the AdventOfCode puzzle
     * @return the AOC implementation to use
     * @throws ClassNotFoundException if year or day doesn't match an exercise already done
     * @throws NoSuchMethodException should not be thrown
     * @throws InvocationTargetException should not be thrown
     * @throws InstantiationException should not be thrown
     * @throws IllegalAccessException should not be thrown
     */
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
