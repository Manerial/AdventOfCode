package aoc.exercises.year2018.day10;

import aoc.AOCRunnerTestBuilder;
import org.junit.jupiter.api.Test;
import utilities.AbstractAOC;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utilities.ResourceIO.DELIMITER;

class AOCRunnerTest {
    @Test
    void test_example() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), true);
        aocRunner.run();
        //@formatter:off
        String sb =
                "#...#..###" + DELIMITER +
                "#...#...#." + DELIMITER +
                "#...#...#." + DELIMITER +
                "#####...#." + DELIMITER +
                "#...#...#." + DELIMITER +
                "#...#...#." + DELIMITER +
                "#...#...#." + DELIMITER +
                "#...#..###" + DELIMITER;
        //@formatter:on
        assertAll(
                () -> assertEquals(sb, aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(3), aocRunner.getSolution2().toString()));
    }

    @Test
    void test_puzzle() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractAOC aocRunner = AOCRunnerTestBuilder.getAOCRunner(this.getClass(), false);
        aocRunner.run();
        //@formatter:off
        String sb =
                ".####...#####......###..#####...#....#..#....#...####...######" + DELIMITER +
                "#....#..#....#......#...#....#..##...#..#...#...#....#..#....." + DELIMITER +
                "#.......#....#......#...#....#..##...#..#..#....#.......#....." + DELIMITER +
                "#.......#....#......#...#....#..#.#..#..#.#.....#.......#....." + DELIMITER +
                "#.......#####.......#...#####...#.#..#..##......#.......#####." + DELIMITER +
                "#.......#...........#...#..#....#..#.#..##......#.......#....." + DELIMITER +
                "#.......#...........#...#...#...#..#.#..#.#.....#.......#....." + DELIMITER +
                "#.......#.......#...#...#...#...#...##..#..#....#.......#....." + DELIMITER +
                "#....#..#.......#...#...#....#..#...##..#...#...#....#..#....." + DELIMITER +
                ".####...#........###....#....#..#....#..#....#...####...#....." + DELIMITER;
        //@formatter:on
        assertAll(
                () -> assertEquals(sb, aocRunner.getSolution1().toString()),
                () -> assertEquals(String.valueOf(10345), aocRunner.getSolution2().toString()));
    }
}