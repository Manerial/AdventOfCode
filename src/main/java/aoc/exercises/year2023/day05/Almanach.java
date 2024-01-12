package aoc.exercises.year2023.day05;


import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public record Almanach(List<Long> seeds, List<Category> categories) {

    Pair<Long, Long> getSeedLocation(long seed) {
        long minRange = Long.MAX_VALUE;
        long currentData = seed;
        for (Category category : categories) {
            for (PlantationData plantationData : category.getPlantationDatas()) {
                if (plantationData.containsSource(currentData)) {
                    minRange = Math.min(minRange, plantationData.getRange() + plantationData.getSourceNumber() - currentData);
                    currentData = plantationData.getDestination(currentData);
                    break;
                }
            }
        }
        return Pair.of(currentData, minRange);
    }
}