package adventofcode.y2022.aoc1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utilities.FileLoader;
import utilities.Printer;

public class AOC1 {
    private static Integer currentCarry = 0;

    public static void run(String file) {
        try {
			// Lecture des inputs
            List<String> list = FileLoader.readListFromFile(file);
			// Création d'une liste à trier
            List<Integer> treeList = new ArrayList<>();
            for (String item : list) {
                getCurrentCarry(treeList, item);
            }
            Collections.sort(treeList);
            // Solution 1 : Le plus
            Printer.println("Solution 1 : " + treeList.get(treeList.size() - 1));

            // Solution 2 : Top 3
            Printer.println("Solution 2 : " + (
                    treeList.get(treeList.size() - 1)
                            + treeList.get(treeList.size() - 2)
                            + treeList.get(treeList.size() - 3)
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getCurrentCarry(List<Integer> treeList, String item) {
        try {
			// Pour chaque entrée du fichier, on la converti et on le somme avec les précédents
            int carry = Integer.parseInt(item);
            currentCarry += carry;
        } catch (NumberFormatException e) {
			// Si on a une erreur de format, c'est qu'on est sur un nouvel enregistrement.
			// On enregistre, on reset et on passe au suivant
            treeList.add(currentCarry);
            currentCarry = 0;
        }
    }
}
