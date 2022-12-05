package aoc3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utilities.FileLoader;

public class AOC3 {
	private static List<String> stringList;
	private static String alphabetOrder = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Lecture des inputs
			List<String> list = FileLoader.readListFromFile("aoc3.txt");
			// On initialise les variables
			int score = 0;
			int incr = 0;
			stringList = new ArrayList<>();
			for(String line : list) {
				incr++;
				// char item = getCommonItemSameLine(line);
				char item = getCommonItem3Lines(line, incr);

				if(item > 0) {					
					score += alphabetOrder.indexOf(item) + 1;
					stringList = new ArrayList<>();
				}
			}
			System.out.println(score);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**<pre>
	 * Retourne le caractère commun entre trois chaines différentes
	 * </pre>
	 * @param string : La chaine à comparer aux deux autres
	 * @param incr : Le numéro de chaine qu'on cherche à analyser
	 * @return
	 */
	private static char getCommonItem3Lines(String string, int incr) {
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
	private static char getCommonItemSameLine(String string) {
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
	private static char getCommonItem() {
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
