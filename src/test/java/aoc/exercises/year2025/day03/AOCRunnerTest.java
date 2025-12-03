package aoc.exercises.year2025.day03;

import aoc.*;
import org.junit.jupiter.api.*;
import utilities.*;

import java.io.*;
import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

class AOCRunnerTest {
    @Test
    void test_example() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), true);
        aocRunner.run();
        assertAll(
                () -> assertEquals(String.valueOf(357), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(3121910778619L), aocRunner.getSolution2().toString()));
    }

    @Test
    void test_puzzle() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), false);
        aocRunner.run();
        assertAll(
                () -> assertEquals(String.valueOf(17196), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(171039099596062L), aocRunner.getSolution2().toString()));
    }
}