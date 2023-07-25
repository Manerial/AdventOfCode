package aoc;

import utilities.AOCArguments;
import utilities.AbstractAOC;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AOCRunnerTestBuilder {
    public static AbstractAOC getAOCRunner(Class<?> testClass, boolean isExample) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Pattern getYear = Pattern.compile("year(\\d{4})");
        Pattern getDay = Pattern.compile("day(\\d{2})");
        String className = testClass.toString();
        int year = Integer.parseInt(extract(getYear, className));
        int day = Integer.parseInt(extract(getDay, className));

        AOCArguments aocArguments = new AOCArguments(year, day, false, isExample, false, "");
        return AbstractAOC.build(aocArguments);
    }

    private static String extract(Pattern pattern, String className) {
        Matcher matcher = pattern.matcher(className);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
