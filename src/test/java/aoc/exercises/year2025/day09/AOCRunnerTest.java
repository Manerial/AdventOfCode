package aoc.exercises.year2025.day09;

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
                () -> assertEquals(String.valueOf(50), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(24), aocRunner.getSolution2().toString()));
    }

    @Test
    void test_puzzle() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), false);
        aocRunner.run();
        assertAll(
                () -> assertEquals(String.valueOf(4755278336L), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(1534043700L), aocRunner.getSolution2().toString()));
    }
}