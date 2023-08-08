package aoc.exercises.year2018.day08;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final List<Node> childs = new ArrayList<>();
    private final List<Integer> metadatas = new ArrayList<>();

    public void addChild(Node child) {
        childs.add(child);
    }

    public void addMetadata(int metadata) {
        metadatas.add(metadata);
    }

    public int getSumMetadata() {
        int sumMetadata = metadatas.stream().mapToInt(Integer::intValue).sum();
        sumMetadata += childs.stream().mapToInt(Node::getSumMetadata).sum();
        return sumMetadata;
    }

    public int getValue() {
        if (childs.isEmpty()) {
            return metadatas.stream().mapToInt(Integer::intValue).sum();
        } else {
            int value = 0;
            for (int metadata : metadatas) {
                if (childs.size() >= metadata) {
                    value += childs.get(metadata - 1).getValue();
                }
            }
            return value;
        }
    }
}
