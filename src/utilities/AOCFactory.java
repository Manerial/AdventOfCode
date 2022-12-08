package utilities;

import adventofcode.aoc_2021_03.AOC_2021_03;
import adventofcode.aoc_2022_07.AOC_2022_07;
import template.AOC;

import org.apache.commons.lang3.NotImplementedException;

import adventofcode.aoc_2021_01.AOC_2021_01;
import adventofcode.aoc_2021_02.AOC_2021_02;
import adventofcode.aoc_2022_01.AOC_2022_01;
import adventofcode.aoc_2022_02.AOC_2022_02;
import adventofcode.aoc_2022_03.AOC_2022_03;
import adventofcode.aoc_2022_04.AOC_2022_04;
import adventofcode.aoc_2022_05.AOC_2022_05;
import adventofcode.aoc_2022_06.AOC_2022_06;

public class AOCFactory {
    public static AOC getAOC(int year, int day) {
        switch (year) {
            case 2022:
                switch (day) {
                    case 1:
                        return new AOC_2022_01();
                    case 2:
                        return new AOC_2022_02();
                    case 3:
                        return new AOC_2022_03();
                    case 4:
                        return new AOC_2022_04();
                    case 5:
                        return new AOC_2022_05();
                    case 6:
                        return new AOC_2022_06();
                    case 7:
                        return new AOC_2022_07();
                    default:
                        throw new NotImplementedException();
                }
            case 2021:
                switch (day) {
                    case 1:
                        return new AOC_2021_01();
                    case 2:
                        return new AOC_2021_02();
                    case 3:
                        return new AOC_2021_03();
                    default:
                        throw new NotImplementedException();
                }
            default:
                throw new NotImplementedException();
        }
    }
}
