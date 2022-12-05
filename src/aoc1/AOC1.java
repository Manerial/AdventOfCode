package aoc1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utilities.FileLoader;

public class AOC1 {
	public static void main(String[] args) {
		try {
			// Lecture des inputs
			List<String> list = FileLoader.readListFromFile(AOC1.class.getSimpleName().toLowerCase() +  ".txt");
			// Création d'une liste à trier
			List<Integer> treeList = new ArrayList<>();
			Integer currentCarry = 0;
			for(String line : list) {
				try {
					// Pour chaque entrée du fichier, on la converti et on le somme avec les précédents
					Integer carry = Integer.parseInt(line);
					currentCarry += carry;
				} catch(NumberFormatException e) {
					// Si on a une erreur de format, c'est qu'on est sur un nouvel enregistrement.
					// On enregistre, on reset et on passe au suivant
					treeList.add(currentCarry);
					currentCarry = 0;
				}
			}
			Collections.sort(treeList);
			// Solution 1 : Le plus
			System.out.println(treeList.get(treeList.size()-1)); 

			// Solution 2 : Top 3
			System.out.println(treeList.get(treeList.size()-1)
						+treeList.get(treeList.size()-2)
						+treeList.get(treeList.size()-3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
