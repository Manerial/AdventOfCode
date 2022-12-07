package adventofcode.aoc_2022_03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

public class AOC_2022_03 extends AOC {
    private static final String ALPHABET_ORDER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private List<String> stringList;

    public void run(String file) {
		try {
			// Inputs reading
            List<String> list = FileLoader.readListFromFile(file);
            Printer.print("Solution 1 : ");
			parseLines(list, true);
            Printer.print("Solution 2 : ");
			parseLines(list, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseLines(List<String> list, boolean sameLine) {
		// Initialization of the variables
		int score = 0;
		int incr = 0;
		char item = 0;
		stringList = new ArrayList<>();
		for(String line : list) {
			if(sameLine) {				
				item = getCommonItemSameLine(line);
			} else {				
				incr++;
				item = getCommonItem3Lines(line, incr);
			}

			if(item > 0) {					
				score += ALPHABET_ORDER.indexOf(item) + 1;
				stringList = new ArrayList<>();
			}
		}
		Printer.println(score);
	}

	/**<pre>
	 * Return the common caractere between 3 lines
	 * </pre>
	 * @param string : The string to compare with the two others
	 * @param incr : The number of the line we are parsing
	 * @return
	 */
	private char getCommonItem3Lines(String string, int incr) {
		stringList.add(string);
		if(incr % 3 == 0) {
			return getCommonItem();
		}
		return 0;
	}

	/**<pre>
	 * Return the common char in a string split in two
	 * </pre>
	 * @param string : The string to parse
	 * @return
	 */
	private char getCommonItemSameLine(String string) {
		stringList.add(string.substring(0, string.length()/2));
		stringList.add(string.substring(string.length()/2));
		char item = getCommonItem();
		return item;
	}

	/**<pre>
	 * Return the common char between any number of lines
	 * </pre>
	 * @return
	 */
	private char getCommonItem() {
		String firstString = stringList.remove(0);
		for(char c : firstString.toCharArray()) {
			boolean found = true;
			for(String string : stringList) {
				found &= string.indexOf(c) >= 0;
			}
			if(found) {
				return c;
			}
		}
		return 0;
	}
}
