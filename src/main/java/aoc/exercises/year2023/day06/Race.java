package aoc.exercises.year2023.day06;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Race {
    private long time;
    private long distance;

    public long getWaysToWin() {
        long minTime = 0;
        long maxTime = time;
        do {
            long firstWinTime = medianTime(minTime, maxTime);
            if (isWinningRace(firstWinTime)) {
                maxTime = firstWinTime;
            } else {
                minTime = firstWinTime;
            }
        } while (minTime != maxTime - 1);

        return time - (2 * maxTime) + 1;
    }

    public long medianTime(long minTime, long maxTime) {
        return (minTime + maxTime) / 2;
    }

    private boolean isWinningRace(long testTime) {
        return (testTime * (time - testTime) - distance) > 0;
    }
}
