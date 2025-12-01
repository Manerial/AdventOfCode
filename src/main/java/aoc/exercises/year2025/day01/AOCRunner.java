package aoc.exercises.year2025.day01;

import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private int lock = 50;
    private int stopAt0 = 0;
    private int passBy0 = 0;

    @Override
    public void run() {
        // Turn string to list of positive and negative integers
        List<Integer> clicksList = inputList.stream()
                .map(s -> s.replace("L", "-").replace("R", ""))
                .map(Integer::parseInt)
                .toList();

        rotateLock(clicksList);
        solution1 = stopAt0;
        solution2 = passBy0;
    }

    /**
     * Rotate the lock by the given number of clicks.
     * If a lock is on 50:
     * <ul>
     * <li>rotating it by 5 clicks will make it on 55;</li>
     * <li>rotating it by -5 clicks will make it on 45;</li>
     * <li>rotating it by 50 clicks will make it on 0, passing by 0 once (and stopping on it);</li>
     * <li>rotating it by -50 clicks will make it on 0, passing by 0 once (and stopping on it);</li>
     * <li>rotating it by -200 clicks will make it on 50, passing by 0 twice;</li>
     * </ul>
     *
     * @param clicksList the list of clicks to apply
     */
    private void rotateLock(List<Integer> clicksList) {
        for (int clicks : clicksList) {
            boolean on0 = lock == 0;
            lock += clicks;
            if (lock >= 100) {
                // if lock >= 100, we passed by 0 at least once
                passBy0 += lock / 100;
            } else if (lock <= 0) {
                // if lock <= 0, we passed at 0 at least once
                // But if we were already on 0, remove one occurrence
                passBy0 -= (lock - 100) / 100 + (on0 ? 1 : 0);
            }
            // Keep the lock on an interval 0-99
            lock = (lock % 100 + 100) % 100;
            if (lock == 0) {
                stopAt0++;
            }
        }
    }
}