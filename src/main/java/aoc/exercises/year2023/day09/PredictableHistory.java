package aoc.exercises.year2023.day09;

import java.util.ArrayList;
import java.util.List;

public class PredictableHistory {
    private List<Integer> history;
    private PredictableHistory extrapolation;

    public PredictableHistory(List<Integer> history) {
        this.history = history;
        if (isFinal()) {
            return;
        }

        List<Integer> extrapolateHistory = new ArrayList<>();
        int current;
        int next;
        current = this.history.get(0);
        for (int i = 1; i < this.history.size(); i++) {
            next = this.history.get(i);
            extrapolateHistory.add(next - current);
            current = next;
        }

        this.extrapolation = new PredictableHistory(extrapolateHistory);
    }

    private boolean isFinal() {
        return this.history.stream().allMatch(i -> i == 0);
    }

    public int findNextValue() {
        if (isFinal()) {
            return 0;
        } else {
            return history.get(history.size() - 1) + this.extrapolation.findNextValue();
        }
    }

    public int findPrevValue() {
        if (isFinal()) {
            return 0;
        } else {
            return history.get(0) - this.extrapolation.findPrevValue();
        }
    }
}
