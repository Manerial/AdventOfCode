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
			// Lecture des inputs
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
		// On initialise les variables
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
	 * Retourne le caractère commun entre trois chaines différentes
	 * </pre>
	 * @param string : La chaine à comparer aux deux autres
	 * @param incr : Le numéro de chaine qu'on cherche à analyser
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
	 * Retourne le caractère commun dans une chaine séparée en deux
	 * </pre>
	 * @param string : La chaine à analyser
	 * @return
	 */
	private char getCommonItemSameLine(String string) {
		// On coupe la chaine en deux sous-chaines
		stringList.add(string.substring(0, string.length()/2));
		stringList.add(string.substring(string.length()/2, string.length()));
		// On récupère le caractère commun entre deux chaines
		char item = getCommonItem();
		return item;
	}

	/**<pre>
	 * Retourne le caractère commun entre n'importe quel nombre de lignes
	 * </pre>
	 * @return
	 */
	private char getCommonItem() {
		// On récupère la première chaine
		String firstString = stringList.remove(0);
		// Pour chaque caractère, on compare avec les autres chaines
		for(char c : firstString.toCharArray()) {
			boolean found = true;
			for(String string : stringList) {
				// Si on a un indexe < 0, ce n'est pas le bon caractère
				found &= string.indexOf(c) >= 0;
			}
			if(found) {
				return c;
			}
		}
		return 0;
	}
}
