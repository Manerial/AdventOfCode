package aoc.exercises.year2024.day21;

import aoc.AOCRunnerTestBuilder;
import org.junit.jupiter.api.Test;
import utilities.AbstractAOC;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AOCRunnerTest {
    @Test
    void test_example() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), true);
        aocRunner.run();
        assertAll(
                () -> assertEquals(String.valueOf(126384), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(154115708116294L), aocRunner.getSolution2().toString()));
    }

    @Test
    void test_puzzle() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), false);
        aocRunner.run();
        assertAll(
                () -> assertEquals(String.valueOf(105458), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(129551515895690L), aocRunner.getSolution2().toString()));
    }
}