package aoc.exercises.year2019.day06;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Planet {
    private final String name;
    private final List<Planet> orbitals = new ArrayList<>();

    public Planet(String name) {
        this.name = name;
    }

    public Planet getOrbital(String name) {
        if(Objects.equals(this.name, name)) {
            return this;
        } else {
            return orbitals.stream()
                    .map(planet -> planet.getOrbital(name))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }
    }

    public void addOrbital(Planet planet) {
        orbitals.add(planet);
    }

    public int getTotalOrbits(int indirectOrbits) {
        return orbitals.stream()
                .mapToInt(value -> value.getTotalOrbits(indirectOrbits + 1))
                .sum() + indirectOrbits;
    }

    public long getDistanceBetween(String p1, String p2) {
        List<Planet> l1 = getPathTo(getOrbital(p1));
        List<Planet> l2 = getPathTo(getOrbital(p2));

        List<Planet> allPlanets = new ArrayList<>();
        allPlanets.addAll(l1);
        allPlanets.addAll(l2);

        return allPlanets.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(planetLongEntry -> planetLongEntry.getValue() == 1)
                .count();
    }

    private List<Planet> getPathTo(Planet planetToReach) {
        Optional<Planet> optNextPlanet = orbitals.stream()
                .filter(currentPlanet -> currentPlanet.hasWayTo(planetToReach))
                .findFirst();

        if(optNextPlanet.isPresent()) {
            List<Planet> path = new ArrayList<>();
            Planet nextPlanet = optNextPlanet.get();
            path.add(nextPlanet);
            path.addAll(nextPlanet.getPathTo(planetToReach));
            return path;
        } else {
            // If not found, we are at destination
            return List.of();
        }
    }

    private boolean hasWayTo(Planet planetToReach) {
        if(orbitals.contains(planetToReach)) {
            return true;
        }

        return orbitals.stream()
                .map(currentPlanet -> currentPlanet.hasWayTo(planetToReach))
                .reduce((b1, b2) -> b1 || b2)
                .orElse(false);
    }
}
