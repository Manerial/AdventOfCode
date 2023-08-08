package aoc.exercises.year2018.day08;

import utilities.AbstractInputParser;

import java.util.Arrays;
import java.util.List;

public class InputParser extends AbstractInputParser<Node> {
    private int parserIndex = 0;
    private List<Integer> nodesData;

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Node parseInput() {
        nodesData = Arrays.stream(inputList.get(0).split(" ")).map(Integer::parseInt).toList();
        return parseNodesRecursive();
    }

    private Node parseNodesRecursive() {
        Node node = new Node();
        int childs = nodesData.get(parserIndex++);
        int headers = nodesData.get(parserIndex++);
        for (int i = 0; i < childs; i++) {
            Node child = parseNodesRecursive();
            node.addChild(child);
        }
        for (int i = 0; i < headers; i++) {
            node.addMetadata(nodesData.get(parserIndex++));
        }
        return node;
    }
}
