package aoc.exercises.year2016.day05;

import utilities.AbstractAOC;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/5">here</a>
 * </pre>
 */
// Can't be optimized
public class AOCRunner extends AbstractAOC {
    record RecordHashes(Set<String> allHashes, Map<Integer, Character> charByPosition) {
    }

    public static final String LEADING_ZERO = "00000";

    public static final int POSITION = LEADING_ZERO.length();

    @Override
    public void run() {
        RecordHashes recordHashes = findDoorPassword();

        solution1 = recordHashes.allHashes.stream()
                .limit(8)
                .map(s -> String.valueOf(s.charAt(POSITION)))
                .collect(Collectors.joining());

        solution2 = recordHashes.charByPosition.values().stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     * We want to find the hashes that start with {@link LEADING_ZERO}
     * @return a {@link RecordHashes}
     */
    private RecordHashes findDoorPassword() {
        String door = inputList.get(0);
        Set<String> allHashes = new LinkedHashSet<>();
        Map<Integer, Character> charByPosition = new HashMap<>();
        int attempt = 0;

        while (!containsAllDigits(charByPosition)) {
            String hash = "";
            while (!hash.startsWith(LEADING_ZERO)) {
                hash = DigestUtils.md5Hex(door + attempt);
                attempt++;
            }
            allHashes.add(hash);
            addCharByPosition(hash, charByPosition);
        }

        return new RecordHashes(allHashes, charByPosition);
    }

    /**
     * Check that charByPosition's keys contains all digits from 1 to 7
     * @param charByPosition : the map to check
     * @return true if charByPosition's keys contains all awaited digits
     */
    private static boolean containsAllDigits(Map<Integer, Character> charByPosition) {
        for(int i = 1; i <= 7; i++) {
            if(!charByPosition.containsKey(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check that the first character of the hash (after the {@link LEADING_ZERO}) is a number.
     * If true, then check it is between 1 and 7.
     * Then, put it in charByPosition if it is absent
     *
     * @param hash : the hash we want to check
     * @param charByPosition : the map to fill
     */
    private void addCharByPosition(String hash, Map<Integer, Character> charByPosition) {
        int position;
        try {
            position = Integer.parseInt(String.valueOf(hash.charAt(POSITION)));
            if(position > 7) {
                return;
            }
        } catch (NumberFormatException ignored) {
            return;
        }
        char charToPrint = hash.charAt(POSITION + 1);
        charByPosition.putIfAbsent(position, charToPrint);
    }
}