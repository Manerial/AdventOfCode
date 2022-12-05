package ca2;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;

public class CA2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<String> list = FileLoader.readListFromFile("ca2.txt");
			int score = 0;
			int vict = 0;
			int deft = 0;
			int draw = 0;
			for(String item : list) {
				item = systeme1(item);
				switch (item) {
				case "C C":
					score++;
				case "B B":
					score++;
				case "A A":
					score++;
					draw++;
					break;
				case "B C":
					score++;
				case "A B":
					score++;
				case "C A":
					score++;
					vict++;
					break;
				case "A C":
					score++;
				case "C B":
					score++;
				case "B A":
					score++;
					deft++;
					break;
				}
			}
			System.out.println(score + deft*0 + draw*3 + vict*6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Solution 1
	private static String systeme1(String item) {
		item = item.replace("X", "A");
		item = item.replace("Y", "B");
		item = item.replace("Z", "C");
		return item;
	}
	
	// Solution 2
	private static String systeme2(String item) {
		if(item.contains("A")) {
			item = item.replace("X", "C");
			item = item.replace("Y", "A");
			item = item.replace("Z", "B");
		}
		if(item.contains("B")) {
			item = item.replace("X", "A");
			item = item.replace("Y", "B");
			item = item.replace("Z", "C");
		}
		if(item.contains("C")) {
			item = item.replace("X", "B");
			item = item.replace("Y", "C");
			item = item.replace("Z", "A");
		}
		return item;
	}
}
