package aoc.exercises.year2023.day05;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Category {
    private String source;
    private String destination;
    private List<PlantationData> plantationDatas = new ArrayList<>();

    public Category(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public void add(long sourceNumber, long destinationNumber, long range) {
        PlantationData plantationData = new PlantationData(sourceNumber, destinationNumber, range);
        plantationDatas.add(plantationData);
    }
}
