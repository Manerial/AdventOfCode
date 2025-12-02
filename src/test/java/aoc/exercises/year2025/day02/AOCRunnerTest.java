package aoc.exercises.year2025.day02;

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
                () -> assertEquals(String.valueOf(1227775554), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(4174379265L), aocRunner.getSolution2().toString()));
    }

    @Test
    void test_puzzle() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), false);
        aocRunner.run();
        assertAll(
                () -> assertEquals(String.valueOf(8576933996L), aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(25663320831L), aocRunner.getSolution2().toString()));
    }
}