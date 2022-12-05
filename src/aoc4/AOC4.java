package aoc4;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;

public class AOC4 {

	public static void main(String[] args) {
		try {
			List<String> list = FileLoader.readListFromFile("aoc4.txt");
			int overlap = 0;
			int contains = 0;
			for(String line : list) {
				// On récupère nos ranges
				Range range1 = getRange(line.split(",")[0]);
				Range range2 = getRange(line.split(",")[1]);
				System.out.println(range1 + " " + range2);
				// Si l'un contient l'autre, on incrémente
				if(range1.contains(range2) || range2.contains(range1)) {
					contains++;
				}

				// Si l'un dépasse sur l'autre, on incrémente
				if(range1.isOverlappedBy(range2) || range2.isOverlappedBy(range1)) {
					overlap++;
				}
			}
			System.out.println("Contains : " + contains);
			System.out.println("Overlap : " + overlap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Range getRange(String rangeStr) {
		int borne1 = Integer.parseInt(rangeStr.split("-")[0]);
		int borne2 = Integer.parseInt(rangeStr.split("-")[1]);
		return new Range(borne1, borne2);
	}

}
