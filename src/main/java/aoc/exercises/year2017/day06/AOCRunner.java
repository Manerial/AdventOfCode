package aoc.exercises.year2017.day06;

import utilities.AbstractAOC;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final Set<String> banksSet = new LinkedHashSet<>();

    @Override
    public void run() {
        int[] duplicatedLoop = dispatchLoop();

        solution1 = banksSet.size();

        List<String> banksList = new ArrayList<>(this.banksSet);
        solution2 = banksList.size() - banksList.indexOf(Arrays.toString(duplicatedLoop));
    }

    private int[] getBanks() {
        return Arrays.stream(inputList.get(0).split("\t"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private int[] dispatchLoop() {
        int[] lastBanks = getBanks();

        while (banksSet.add(Arrays.toString(lastBanks))) {
            int[] newBanks = Arrays.copyOf(lastBanks, lastBanks.length);
            int currentMax = Arrays.stream(lastBanks).max().orElseThrow();

            for(int index = 0; index < newBanks.length; index++) {
                if(newBanks[index] == currentMax) {
                    dispatch(currentMax, newBanks, index);
                    break;
                }
            }
            lastBanks = newBanks;

        }
        return lastBanks;
    }

    private static void dispatch(int currentMax, int[] newBanks, int index) {
        int currentIndex = index;
        newBanks[currentIndex] = 0;
        for(int decrement = currentMax; decrement > 0; decrement--) {
            currentIndex++;
            newBanks[currentIndex%newBanks.length] += 1;
        }
    }
}
