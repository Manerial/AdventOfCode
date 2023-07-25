package aoc.exercises.year2016.day04;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RoomData {
    public static final int ALPHABET_SIZE = 26;
    @Getter
    private int id;
    private String name;
    private String checksum;

    public boolean isReal() {
        Map<String, Long> charFrequencies = getCharFrequencies();

        String newCheckSum = charFrequencies.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // filter Alphabetical
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) // filter by frequency decreasing
                .limit(5) // get top five
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(""));

        return newCheckSum.equals(checksum);
    }

    private Map<String, Long> getCharFrequencies() {
        String cleanName = name.replace("-", "");

        return cleanName.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public String uncypherRoomName() {
        StringBuilder output = new StringBuilder();

        for (int position = 0; position < name.length(); position++) {
            char character = name.charAt(position);
            char newChar = shiftCharacter(character, id);
            output.append(newChar);
        }

        return output.toString();
    }

    private char shiftCharacter(char character, int shift) {
        if (Character.isLetter(character)) {
            int originalAlphabetPosition = character - 'a';
            int shiftedAlphabetPosition = (originalAlphabetPosition + shift) % ALPHABET_SIZE;
            return (char) ('a' + shiftedAlphabetPosition);
        }
        // character = '-'
        return ' ';
    }

}
