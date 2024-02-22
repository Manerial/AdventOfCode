package aoc.exercises.year2015.day09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Town {
    private final String name;
    private final Map<Town, Integer> connectedTowns = new HashMap<>();

    public Town(String name) {
        this.name = name;
    }

    public boolean is(String name) {
        return this.name.equals(name);
    }

    public void add(Town town, int distance) {
        connectedTowns.put(town, distance);
    }

    public int getDistance(Town nextTown) {
        return connectedTowns.get(nextTown);
    }

    /**
     * Get the list of all paths possibles to visit all towns.
     *
     * @return a list of all paths to visit all towns.
     */
    public List<Path> getAllPaths() {
        return getAllPaths(new Path());
    }

    /**
     * Recursive function to get all paths from this town to all other towns.
     *
     * @param currentPath : current path we are following
     * @return a list of all paths from this town to all others
     */
    private List<Path> getAllPaths(Path currentPath) {
        // Action
        currentPath.add(this);

        List<Town> townsToVisit = connectedTowns.keySet().stream()
                .filter(t -> !currentPath.contains(t))
                .toList();

        // Break condition
        if (townsToVisit.isEmpty()) {
            return List.of(currentPath);
        }

        // Recursive call
        List<Path> paths = new ArrayList<>();
        for (Town connectedTown : townsToVisit) {
            Path newPath = new Path(currentPath);
            paths.addAll(connectedTown.getAllPaths(newPath));
        }
        return paths;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
