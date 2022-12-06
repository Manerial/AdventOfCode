package utilities;

import template.AOC;

import org.apache.commons.lang3.NotImplementedException;

import adventofcode.y2022.aoc1.AOC1;
import adventofcode.y2022.aoc2.AOC2;
import adventofcode.y2022.aoc3.AOC3;
import adventofcode.y2022.aoc4.AOC4;
import adventofcode.y2022.aoc5.AOC5;
import adventofcode.y2022.aoc6.AOC6;

public class AOCFactory {
	public static AOC getAOC(int year, int day) {
		switch (year) {
		case 2022:
			switch (day) {
			case 1:
				return new AOC1();
			case 2:
				return new AOC2();
			case 3:
				return new AOC3();
			case 4:
				return new AOC4();
			case 5:
				return new AOC5();
			case 6:
				return new AOC6();
			default:
				throw new NotImplementedException();
			}
		case 2021:
			switch (day) {
			case 1:
				return new adventofcode.y2021.aoc1.AOC1();
			case 2:
				return new adventofcode.y2021.aoc2.AOC2();
			default:
				throw new NotImplementedException();
			}
		default:
			throw new NotImplementedException();
		}
	}
}
