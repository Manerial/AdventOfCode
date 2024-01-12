package aoc.exercises.year2023.day05;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlantationData {
    private long sourceNumber;
    private long destinationNumber;
    private long range;

    public boolean containsSource(long number) {
        return sourceNumber <= number && number <= sourceNumber + range;
    }

    public long getDestination(long number) {
        return number - sourceNumber + destinationNumber;
    }
}
