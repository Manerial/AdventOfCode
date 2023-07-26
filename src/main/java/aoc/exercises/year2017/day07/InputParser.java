package aoc.exercises.year2017.day07;

import utilities.AbstractInputParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser extends AbstractInputParser<Program> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Program parseInput() {
        inputList.sort(Comparator.comparingInt(String::length));

        Map<String, Program> programMap = getProgramMap();

        addAboves(programMap);

        return programMap.values().stream()
                .max(Comparator.comparingInt(Program::countChilds))
                .orElseThrow();
    }

    private Map<String, Program> getProgramMap() {
        Pattern pattern1 = Pattern.compile("([a-z]*) \\((\\d*)\\)");
        Map<String, Program> programMap = new HashMap<>();

        for (String line : inputList) {
            Matcher matcher = pattern1.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(1);
                int weight = Integer.parseInt(matcher.group(2));
                Program program = new Program(name, weight);
                programMap.put(name, program);
            }
        }
        return programMap;
    }

    private void addAboves(Map<String, Program> programMap) {
        Pattern pattern2 = Pattern.compile("([a-z]*) \\(\\d*\\) -> (.*)");
        for (String line : inputList) {
            Matcher matcher = pattern2.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(1);
                Program program = programMap.get(name);

                List<String> aboves = new ArrayList<>(List.of(matcher.group(2).split(", ")));
                for (String above : aboves) {
                    program.addAbove(programMap.get(above));
                }
            }
        }
    }
}
