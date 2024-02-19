package aoc.exercises.year2022.day19;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/19">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    List<RobotFactory> robotFactories = new ArrayList<>();

    @Override
    public void run() {
        for (String blueprint : inputList) {
            RobotFactory robotFactory = new RobotFactory(blueprint);
            robotFactories.add(robotFactory);
        }
        robotFactories.forEach(robotFactory -> robotFactory.runFor(24));
        solution1 = robotFactories.stream().mapToInt(RobotFactory::getBlueprintQuality).sum();

        robotFactories.stream()
                .limit(3)
                .forEach(robotFactory -> robotFactory.runFor(32));
        solution2 = robotFactories.stream().map(RobotFactory::getMaxGeodes)
                .limit(3)
                .reduce((left, right) -> left * right)
                .orElse(0);
    }
}