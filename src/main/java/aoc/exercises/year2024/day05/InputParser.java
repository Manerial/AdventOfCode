package aoc.exercises.year2024.day05;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<PageUpdater> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public PageUpdater parseInput() {
        PageUpdater pageUpdater = new PageUpdater();
        for (String line : inputList) {
            if (line.contains("|")) {
                pageUpdater.addPageOrderRule(new PageOrderRule(line));
            } else if (line.contains(",")) {
                pageUpdater.addPageUpdate(new PageUpdate(line));
            }
        }
        return pageUpdater;
    }
}
