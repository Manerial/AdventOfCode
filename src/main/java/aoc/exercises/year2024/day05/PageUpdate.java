package aoc.exercises.year2024.day05;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class PageUpdate {
    private List<Integer> pagesUpdated;

    public PageUpdate(PageUpdate pageUpdate) {
        this.pagesUpdated = new ArrayList<>(pageUpdate.pagesUpdated);
    }

    public PageUpdate(String line) {
        pagesUpdated = new ArrayList<>(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
    }

    public boolean matchRules(List<PageOrderRule> pageOrderRules) {
        for (PageOrderRule rule : pageOrderRules) {
            if (failsRule(rule)) {
                return false;
            }
        }
        return true;
    }

    private boolean failsRule(PageOrderRule pageOrderRule) {
        int indexOfPageBefore = pagesUpdated.indexOf(pageOrderRule.getPageBefore());
        int indexOfPageAfter = pagesUpdated.indexOf(pageOrderRule.getPageAfter());
        return indexOfPageBefore != -1 && indexOfPageAfter != -1 && indexOfPageBefore > indexOfPageAfter;
    }

    public int getMiddlePage() {
        return pagesUpdated.get(pagesUpdated.size() / 2);
    }

    public PageUpdate getCorrectOrder(List<PageOrderRule> pageOrderRules) {
        PageUpdate pageUpdate = new PageUpdate(this);
        pageUpdate.sort(pageOrderRules);
        return pageUpdate;
    }

    public void sort(List<PageOrderRule> pageOrderRules) {
        while (!this.matchRules(pageOrderRules)) {
            for (PageOrderRule rule : pageOrderRules) {
                if (this.failsRule(rule)) {
                    permute(rule);
                }
            }
        }
    }

    private void permute(PageOrderRule rule) {
        int indexOfPageBefore = pagesUpdated.indexOf(rule.getPageBefore());
        pagesUpdated.remove((Integer) rule.getPageAfter()); // Remove the PageAfter object from the list
        pagesUpdated.add(indexOfPageBefore, rule.getPageAfter()); // Insert the PageAfter object at the PageBefore position
    }
}
