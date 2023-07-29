package aoc.exercises.year2020.day07;

import utilities.AbstractInputParser;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser extends AbstractInputParser<Collection<Bag>> {
    Pattern extractBag = Pattern.compile("(.*) bags contain ");
    Pattern extractBagContent = Pattern.compile(".* bags contain (?!no other)(.*)");
    Pattern extractContentInfo = Pattern.compile("(\\d+) (.*) bag");

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Collection<Bag> parseInput() {
        Map<String, Bag> bagMap = new HashMap<>();

        inputList.forEach(line -> addBag(bagMap, line));

        return bagMap.values();
    }

    private void addBag(Map<String, Bag> bagMap, String line) {
        Matcher matcherBag = extractBag.matcher(line);
        if (matcherBag.find()) {
            Bag bag = getOrSaveBag(bagMap, matcherBag.group(1));
            searchContent(bagMap, line, bag);
        }
    }

    private void searchContent(Map<String, Bag> bagMap, String line, Bag bag) {
        Matcher matcherBagContent = extractBagContent.matcher(line);
        if (matcherBagContent.find()) {
            String[] splittedContent = matcherBagContent.group(1).split(", ");
            for (String containedBagStr : splittedContent) {
                addContent(bagMap, bag, containedBagStr);
            }
        }
    }

    private void addContent(Map<String, Bag> bagMap, Bag bag, String containedBagStr) {
        Matcher matcherContentInfo = extractContentInfo.matcher(containedBagStr);
        if (matcherContentInfo.find()) {
            int number = Integer.parseInt(matcherContentInfo.group(1));
            String bagName = matcherContentInfo.group(2);
            Bag containedBag = getOrSaveBag(bagMap, bagName);
            bag.addContent(containedBag, number);
        }
    }

    private static Bag getOrSaveBag(Map<String, Bag> bagMap, String bagName) {
        Bag bag = bagMap.get(bagName);
        if (bag == null) {
            bag = new Bag(bagName);
            bagMap.putIfAbsent(bagName, bag);
        }
        return bag;
    }
}
