package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Join AdventOfCode <a href="https://adventofcode.com">here</a>
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AOCArguments aocArguments = new AOCArguments(args);

        String message = aocArguments.getMessage();
        if (message != null) {
            logger.info(message);
            return;
        }

        AbstractAOC aoc = AbstractAOC.build(aocArguments);
        runAOC(aoc, aocArguments.isPrint());
    }

    private static void runAOC(AbstractAOC aoc, boolean isPrint) {
        aoc.run();

        if (isPrint) {
            String solution1 = String.format("Solution 1 : %s", aoc.getSolution1());
            String solution2 = String.format("Solution 2 : %s", aoc.getSolution2());
            logger.info(solution1);
            logger.info(solution2);
        }
    }
}
