package aoc.exercises.year2025.day11;

import utilities.*;

import java.util.*;

public class InputParser extends AbstractInputParser<Map<String, Node>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<String, Node> parseInput() {
        Map<String, Node> nodes = new HashMap<>();
        for (String line : inputList) {
            String name = line.split(":")[0];
            Node node = new Node(name);
            nodes.put(name, node);
        }
        nodes.put("out", new Node("out"));
        for (String line : inputList) {
            String[] split = line.split(":");
            String name = split[0];
            String[] neighbors = split[1].trim().split(" ");
            Map<String, Node> currentNodeNeighbors = nodes.get(name).getNeighbors();
            for (String neighbor : neighbors) {
                currentNodeNeighbors.put(neighbor, nodes.get(neighbor));
            }
        }
        return nodes;
    }
}
