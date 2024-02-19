package aoc.exercises.year2022.day19;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RobotFactory {
    private final int blueprintId;
    Map<Material, Map<Material, Integer>> robotCosts = new EnumMap<>(Material.class);
    Map<Material, Integer> maxCosts = new EnumMap<>(Material.class);
    @Getter
    private int maxGeodes;
    private int maxTime = 0;

    public RobotFactory(String blueprint) {
        String[] parts = blueprint // Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                .replace("Blueprint ", "") // 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                .replace(": Each ore robot costs ", ";") // 1;4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                .replace(" ore. Each clay robot costs ", ";") // 1;4;2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                .replace(" ore. Each obsidian robot costs ", ";") // 1;4;2;3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                .replace(" clay. Each geode robot costs ", ";") // 1;4;2;3 ore and 14;2 ore and 7 obsidian.
                .replace(" ore and ", ",") // 1;4;2;3,14;2,7 obsidian.
                .replace(" obsidian.", "") // 1;4;2;3,14;2,7
                .split(";"); // ["1", "4", "2", "3,14", "2,7"]
        this.blueprintId = Integer.parseInt(parts[0]);

        Map<Material, Integer> oreRobots = new EnumMap<>(Material.class);
        Map<Material, Integer> clayRobots = new EnumMap<>(Material.class);
        Map<Material, Integer> obsidianRobots = new EnumMap<>(Material.class);
        Map<Material, Integer> geodeRobots = new EnumMap<>(Material.class);

        oreRobots.put(Material.ORE, Integer.parseInt(parts[1]));
        clayRobots.put(Material.ORE, Integer.parseInt(parts[2]));
        obsidianRobots.put(Material.ORE, Integer.parseInt(parts[3].split(",")[0]));
        obsidianRobots.put(Material.CLAY, Integer.parseInt(parts[3].split(",")[1]));
        geodeRobots.put(Material.ORE, Integer.parseInt(parts[4].split(",")[0]));
        geodeRobots.put(Material.OBSIDIAN, Integer.parseInt(parts[4].split(",")[1]));

        robotCosts.put(Material.ORE, oreRobots);
        robotCosts.put(Material.CLAY, clayRobots);
        robotCosts.put(Material.OBSIDIAN, obsidianRobots);
        robotCosts.put(Material.GEODE, geodeRobots);

        int maxOre = Stream.of(
                        oreRobots.get(Material.ORE),
                        clayRobots.get(Material.ORE),
                        obsidianRobots.get(Material.ORE),
                        geodeRobots.get(Material.ORE))
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
        maxCosts.put(Material.ORE, maxOre);
        maxCosts.put(Material.CLAY, obsidianRobots.get(Material.CLAY));
        maxCosts.put(Material.OBSIDIAN, geodeRobots.get(Material.OBSIDIAN));
    }

    public int getBlueprintQuality() {
        return maxGeodes * blueprintId;
    }

    /**
     * Start the factory for the given number of time units.
     *
     * @param maxTime : the maximum number of time units to run the factory for
     */
    public void runFor(int maxTime) {
        this.maxTime = maxTime;

        Map<Material, Integer> robots = new EnumMap<>(Material.class);
        robots.put(Material.ORE, 1);
        robots.put(Material.CLAY, 0);
        robots.put(Material.OBSIDIAN, 0);
        robots.put(Material.GEODE, 0);

        Map<Material, Integer> resources = new EnumMap<>(Material.class);
        resources.put(Material.ORE, 0);
        resources.put(Material.CLAY, 0);
        resources.put(Material.OBSIDIAN, 0);
        resources.put(Material.GEODE, 0);

        runRecursive(robots, resources, 0, List.of());
    }

    /**
     * Recursively explore all the possibilities (branches) of building robots we can.
     *
     * @param robots     : the robots we have in stock
     * @param resources  : the resources we have in stock
     * @param times      : the current time
     * @param couldBuild : the robots we could have build in previous step
     */
    private void runRecursive(Map<Material, Integer> robots, Map<Material, Integer> resources, int times, List<Material> couldBuild) {
        times += 1;
        if (times == maxTime) {
            if (maxGeodes < (resources.get(Material.GEODE) + robots.get(Material.GEODE))) {
                maxGeodes = resources.get(Material.GEODE) + robots.get(Material.GEODE);
            }
            return;
        }
        if (hasResourcesFor(Material.GEODE, resources)) {
            buildAndRun(Material.GEODE, robots, resources, times, List.of());
        } else {
            List<Material> canBuild = new ArrayList<>();
            checkThenBuild(Material.OBSIDIAN, robots, resources, times, couldBuild, canBuild);
            checkThenBuild(Material.CLAY, robots, resources, times, couldBuild, canBuild);
            checkThenBuild(Material.ORE, robots, resources, times, couldBuild, canBuild);
            buildAndRun(Material.NONE, robots, resources, times, canBuild);
        }
    }

    /**
     * Checks if there is enough resources to produce the given material and if we should build it.
     *
     * @param material   : the type of robot we want to build
     * @param robots     : the robots we have in stock
     * @param resources  : the resources we have in stock
     * @param times      : the current time
     * @param couldBuild : the robots we could have build in previous step
     * @param canBuild   : the robots we can build in the current step
     */
    private void checkThenBuild(Material material, Map<Material, Integer> robots, Map<Material, Integer> resources, int times, List<Material> couldBuild, List<Material> canBuild) {
        if (hasResourcesFor(material, resources)) {
            canBuild.add(material);
            if (shouldBuild(material, robots, couldBuild)) {
                buildAndRun(material, robots, resources, times, List.of());
            }
        }
    }

    /**
     * Collect resources then check if we could crack more geodes.
     * If we can, then build the given robot and continue exploring the branch.
     *
     * @param material   : the type of robot we want to build
     * @param robots     : the robots we have in stock
     * @param resources  : the resources we have in stock
     * @param times      : the current time
     * @param couldBuild : the robots we could have build in previous step
     */
    private void buildAndRun(Material material, Map<Material, Integer> robots, Map<Material, Integer> resources, int times, List<Material> couldBuild) {
        Map<Material, Integer> newRobots = new EnumMap<>(robots);
        Map<Material, Integer> newResources = new EnumMap<>(resources);
        collectResources(newRobots, newResources);

        if (!canProduceMore(newRobots, newResources, times)) {
            return;
        }
        if (material != Material.NONE) {
            newRobots.put(material, newRobots.get(material) + 1);
            payCost(material, newResources);
        }

        runRecursive(newRobots, newResources, times, couldBuild);
    }

    /**
     * Allows all the robots to produce resources.
     *
     * @param robots    : the robots we have in stock
     * @param resources : the resources we have in stock
     */
    private void collectResources(Map<Material, Integer> robots, Map<Material, Integer> resources) {
        robots.forEach((key, value) ->
                resources.put(key, resources.get(key) + value)
        );
    }

    /**
     * Cheats to produce more geodes. If the cheat does not produce a better amount than we already have, we should skip the branch.
     *
     * @param robots    : the robots we have in stock
     * @param resources : the resources we have in stock
     * @param times     : the current time
     * @return true if the cheat allows us to produce more geodes than the current max, false otherwise
     */
    private boolean canProduceMore(Map<Material, Integer> robots, Map<Material, Integer> resources, int times) {
        int timeLeft = maxTime - times;
        int maxProduction = resources.get(Material.GEODE);
        maxProduction += timeLeft * robots.get(Material.GEODE);
        maxProduction += (timeLeft + 1) * timeLeft / 2;
        return maxProduction > maxGeodes;
    }

    /**
     * After building the robot, we pay its cost.
     *
     * @param material  : the type of robot we want to build
     * @param resources : the resources we have in stock
     */
    private void payCost(Material material, Map<Material, Integer> resources) {
        for (Map.Entry<Material, Integer> cost : robotCosts.get(material).entrySet()) {
            Material costMaterial = cost.getKey();
            Integer costValue = cost.getValue();
            resources.put(costMaterial, resources.get(costMaterial) - costValue);
        }
    }

    /**
     * Checks if there is enough resources to produce the given robot.
     *
     * @param material  : the type of robot we want to build
     * @param resources : the resources we have in stock
     * @return true if there is enough resources to produce the robot, false otherwise
     */
    private boolean hasResourcesFor(Material material, Map<Material, Integer> resources) {
        for (Map.Entry<Material, Integer> cost : robotCosts.get(material).entrySet()) {
            Material costMaterial = cost.getKey();
            Integer costValue = cost.getValue();
            if (resources.get(costMaterial) < costValue) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if we should build the given robot. We should not if there is already plenty of it and if we could build it in previous step.
     *
     * @param material   : the type of robot we want to build
     * @param robots     : the robots we have in stock
     * @param couldBuild : the robots we could have build in previous step
     * @return
     */
    private boolean shouldBuild(Material material, Map<Material, Integer> robots, List<Material> couldBuild) {
        return couldNotBeBuilt(material, couldBuild) && isNotMaxed(robots, material);
    }

    /**
     * Check if we could have build the robot in previous step.
     *
     * @param material   : the type of robot we want to build
     * @param couldBuild : the robots we could have build in previous step
     * @return
     */
    private static boolean couldNotBeBuilt(Material material, List<Material> couldBuild) {
        return !couldBuild.contains(material);
    }

    private boolean isNotMaxed(Map<Material, Integer> robots, Material material) {
        return robots.get(material) < maxCosts.get(material);
    }
}
