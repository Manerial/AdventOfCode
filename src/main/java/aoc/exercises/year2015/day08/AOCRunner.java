package aoc.exercises.year2015.day08;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int totalLength = 0;
        int decodedLength = 0;
        int encodedLength = 0;
        for (String line : inputList) {
            totalLength += line.length();
            decodedLength += getDecodedLength(line);
            encodedLength += getEncodedLength(line);
        }

        solution1 = totalLength - decodedLength; // Deleted characters
        solution2 = encodedLength - totalLength; // Inserted characters
    }

    /**
     * Decode the string and return its length:
     * "aaa\"aaa" -> aaa"aaa
     *
     * @param line : the string to decode
     * @return the decoded string length
     */
    private int getDecodedLength(String line) {
        return line
                .replace("\\\"", "\"") // \" -> "
                .replace("\\\\", "\\") // \\ -> \
                .replaceAll("\\\\x[0-9a-f]{2}", "0") // \\x2f -> 0
                .length() - 2;
    }

    /**
     * Over encode the string and return its length :
     * "aaa\"aaa" -> "\"aaa\\\"aaa\""
     *
     * @param line : the string to encode
     * @return the encoded string length
     */
    private int getEncodedLength(String line) {
        return line
                .replace("\\", "\\\\") // \ -> \\
                .replace("\"", "\\\"") // " -> \"
                .length() + 2;
    }
}
