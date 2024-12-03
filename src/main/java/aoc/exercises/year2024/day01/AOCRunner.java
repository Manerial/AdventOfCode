package aoc.exercises.year2024.day01;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    List<Long> rightList = new ArrayList<>();
    List<Long> leftList = new ArrayList<>();
    List<Long> distanceList = new ArrayList<>();
    List<Long> similarityList = new ArrayList<>();

    @Override
    public void run() {
        fillRightAndLeft();

        sortRightAndLeft();

        extractDistanceAndSimilarity();

        solution1 = distanceList.stream()
                .mapToLong(Long::longValue)
                .sum();
        solution2 = similarityList.stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private void fillRightAndLeft() {
        inputList.forEach(line -> {
            String[] split = line.split(" {3}");
            rightList.add(Long.parseLong(split[0].trim()));
            leftList.add(Long.parseLong(split[1].trim()));
        });
    }

    private void sortRightAndLeft() {
        rightList.sort(Long::compareTo);
        leftList.sort(Long::compareTo);
    }

    private void extractDistanceAndSimilarity() {
        for(int i = 0; i < rightList.size(); i++) {
            long rightNumber = rightList.get(i);
            long distance = Math.abs(rightNumber - leftList.get(i));
            long similarity = leftList.stream()
                    .filter(num -> Objects.equals(num, rightNumber))
                    .count() * rightNumber;
            distanceList.add(distance);
            similarityList.add(similarity);
        }
    }
}