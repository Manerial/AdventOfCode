package aoc.exercises.year2023.day08;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesertMap {
    private List<Character> directions;
    private Map<String, Pair<String, String>> desertNodes = new HashMap<>();

    public DesertMap(List<Character> directions) {
        this.directions = directions;
    }

    public void addDesertNode(String position, String left, String right) {
        desertNodes.put(position, Pair.of(left, right));
    }

    public int getStepsToEnd() {
        int steps = 0;
        String currentPosition = "AAA";
        while (!currentPosition.equals("ZZZ")) {
            char direction = directions.get(steps % directions.size());
            if (direction == 'L') {
                currentPosition = desertNodes.get(currentPosition).getLeft();
            } else {
                currentPosition = desertNodes.get(currentPosition).getRight();
            }
            steps++;
        }
        return steps;
    }

    public long getGhostStepsToEnd() {
        List<Ghost> ghosts = getGhosts();
        List<Ghost> endedGhosts = new ArrayList<>();
        runGhosts(ghosts, endedGhosts);
        return endedGhosts.stream()
                .map(Ghost::getLoopSteps)
                .mapToLong(l -> l / this.directions.size())
                .reduce((l1, l2) -> l1 * l2)
                .orElseThrow() * this.directions.size();
    }

    private int runGhosts(List<Ghost> ghosts, List<Ghost> endedGhosts) {
        int steps = 0;
        while (!ghosts.isEmpty()) {
            char direction = directions.get(steps % directions.size());
            steps++;
            for (Ghost ghost : ghosts) {
                if (direction == 'L') {
                    ghost.addStep(desertNodes.get(ghost.getLastStep()).getLeft() + steps % directions.size());
                } else {
                    ghost.addStep(desertNodes.get(ghost.getLastStep()).getRight() + steps % directions.size());
                }
                if (ghost.isFinished()) {
                    endedGhosts.add(ghost);
                }
            }
            ghosts.removeAll(endedGhosts);
        }
        return steps;
    }

    private List<Ghost> getGhosts() {
        return new ArrayList<>(desertNodes.keySet().stream()
                .filter(s -> s.endsWith("A"))
                .map(s -> {
                    Ghost newGhost = new Ghost();
                    newGhost.addStep(s + "0");
                    return newGhost;
                })
                .toList());
    }
}
