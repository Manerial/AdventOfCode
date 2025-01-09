package aoc.exercises.year2024.day22;

import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/22">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final List<Pair<List<Integer>, List<Integer>>> buyersBananasAndChanges = new ArrayList<>();

    @Override
    public void run() {
        solution1 = inputList.stream()
                .map(Long::parseLong)
                .mapToLong(this::compute2000)
                .sum();
        solution2 = getMaxBananas();
    }

    private long compute2000(long secretNumber) {
        List<Integer> buyerBananas = new ArrayList<>();
        List<Integer> buyerChanges = new ArrayList<>();
        buyersBananasAndChanges.add(Pair.of(buyerBananas, buyerChanges));

        long newSecret = secretNumber;
        int previousBananas = (int) (secretNumber % 10);
        for (int i = 0; i < 2000; i++) {
            newSecret = compute(newSecret);
            int bananas = (int) (newSecret % 10);
            buyerBananas.add(bananas);
            buyerChanges.add(bananas - previousBananas);
            previousBananas = bananas;
        }

        return newSecret;
    }

    private int getMaxBananas() {
        Map<List<Integer>, Integer> bananasByChanges = new HashMap<>();
        buyersBananasAndChanges.forEach(buyerBananasAndChanges -> {
            Set<List<Integer>> distinctChanges = new HashSet<>();
            List<Integer> buyerBananas = buyerBananasAndChanges.getLeft();
            List<Integer> buyerChanges = buyerBananasAndChanges.getRight();
            for (int i = 0; i < buyerChanges.size() - 3; i++) {
                List<Integer> currentChange = buyerChanges.subList(i, i + 4);
                boolean saved = distinctChanges.add(currentChange);
                if (saved) {
                    int bananas = buyerBananas.get(i + 3);
                    bananasByChanges.put(currentChange, bananasByChanges.getOrDefault(currentChange, 0) + bananas);
                }
            }
        });

        return bananasByChanges.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
    }

    private long compute(long secretNumber) {
        long result = prune(mix(secretNumber, secretNumber * 64));
        result = prune(mix(result, result / 32));
        result = prune(mix(result, result * 2048));
        return result;
    }

    private long mix(long secretNumber, long value) {
        return secretNumber ^ value;
    }

    private long prune(long secretNumber) {
        return secretNumber % 16777216;
    }
}