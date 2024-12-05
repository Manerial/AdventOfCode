package aoc.exercises.year2024.day05;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageUpdater {
    private List<PageOrderRule> pageOrderRules = new ArrayList<>();
    private List<PageUpdate> pageUpdates = new ArrayList<>();

    public List<PageUpdate> getMatchingUpdates() {
        return pageUpdates.stream()
                .filter(pageUpdate -> pageUpdate.matchRules(pageOrderRules))
                .toList();
    }

    public List<PageUpdate> getNonMatchingUpdatesCorrected() {
        return pageUpdates.stream()
                .filter(pageUpdate -> !pageUpdate.matchRules(pageOrderRules))
                .map(pageUpdate -> pageUpdate.getCorrectOrder(pageOrderRules))
                .toList();
    }

    public void addPageUpdate(PageUpdate pageUpdate) {
        pageUpdates.add(pageUpdate);
    }

    public void addPageOrderRule(PageOrderRule pageOrderRule) {
        pageOrderRules.add(pageOrderRule);
    }
}
