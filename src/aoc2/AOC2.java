package aoc2;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;

public class AOC2 {
	public static void main(String[] args) {
		try {
			// Lecture des inputs
			List<String> list = FileLoader.readListFromFile("aoc2.txt");
			// On initialise le score à 0
			int score, vict, deft, draw;
			score = vict = deft = draw = 0;
			
			for(String line : list) {
				// On bascule dans le système 1 ou 2
				line = systeme1(line);
				line = systeme2(line);
				// On récupère le score du coup joué
				score += getGameScore(line);
				// On regarde quel est le bon cas de figure
				switch (line) {
				case "C C":
				case "B B":
				case "A A":
					draw++;
					break;
				case "B C":
				case "A B":
				case "C A":
					vict++;
					break;
				case "A C":
				case "C B":
				case "B A":
					deft++;
					break;
				}
			}
			System.out.println("Score final : " + score + deft*0 + draw*3 + vict*6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int getGameScore(String string) {
		int score = 0;
		switch (string.split(" ")[1]) {
		case "C":
			score = 3;
			break;
		case "B":
			score = 2;
			break;
		case "A":
			score = 1;
			break;
		}
		return score;
	}
	
	/**<pre>
	 * Système 1 :
	 *  A = X = Pierre
	 *  B = Y = Feuille
	 *  Z = Z = Ciseau
	 * </pre>
	 * @param string : le coup joué dans la stratégie
	 * @return le coup joué avec la transposition du second selon A, B ou C
	 */
	private static String systeme1(String string) {
		string = string.replace("X", "A");
		string = string.replace("Y", "B");
		string = string.replace("Z", "C");
		return string;
	}

	/**<pre>
	 * Système 2 :
	 *  La seconde entrée correspond au résultat :
	 *   X = défaite, Y = Match nul, Z = Victoire
	 *  Si A (Pierre), X = Ciseau,  Y = Pierre,  Z = Feuille
	 *  Si B (Feuille), X = Pierre,  Y = Feuille,  Z = Ciseau
	 *  Si C (Ciseau), X = Feuille,  Y = Ciseau,  Z = Pierre
	 * </pre>
	 * @param string : le coup joué dans la stratégie
	 * @return le coup joué avec la transposition du second selon A, B ou C
	 */
	private static String systeme2(String string) {
		if(string.contains("A")) {
			string = string.replace("X", "C");
			string = string.replace("Y", "A");
			string = string.replace("Z", "B");
		}
		if(string.contains("B")) {
			string = string.replace("X", "A");
			string = string.replace("Y", "B");
			string = string.replace("Z", "C");
		}
		if(string.contains("C")) {
			string = string.replace("X", "B");
			string = string.replace("Y", "C");
			string = string.replace("Z", "A");
		}
		return string;
	}
}
