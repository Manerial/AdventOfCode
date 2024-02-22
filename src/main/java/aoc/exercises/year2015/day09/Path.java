package aoc.exercises.year2015.day09;

import java.util.ArrayList;
import java.util.List;

public class Path {
    List<Town> listOfTown = new ArrayList<>();

    public Path() {
    }

    public Path(Path currentPath) {
        this.listOfTown = new ArrayList<>(currentPath.listOfTown);
    }

    public void add(Town town) {
        this.listOfTown.add(town);
    }

    public boolean contains(Town town) {
        return this.listOfTown.contains(town);
    }

    /**
     * Follow the path and return the total distance between the towns.
     *
     * @return the total distance between the towns.
     */
    public int computeDistance() {
        int distance = 0;
        for (int townIndex = 0; townIndex < listOfTown.size() - 1; townIndex++) {
            Town town = listOfTown.get(townIndex);
            Town nextTown = listOfTown.get(townIndex + 1);
            distance += town.getDistance(nextTown);
        }
        return distance;
    }
}
