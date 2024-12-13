package aoc.exercises.year2024.day13;

import aoc.common_objects.Position;
import lombok.Getter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class ClawMachina {
    private final Position buttonA;
    private final Position buttonB;
    private final PrizeCoordinates prize;

    public ClawMachina(List<String> machinaSpecs) {
        /*
        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450
        */
        this.buttonA = parseXY(machinaSpecs.get(0));
        this.buttonB = parseXY(machinaSpecs.get(1));
        this.prize = new PrizeCoordinates(parseXY(machinaSpecs.get(2)));
    }

    private Position parseXY(String spec) {
        Pattern pattern = Pattern.compile("X[=+](\\d+), Y[=+](\\d+)");
        Matcher matcher = pattern.matcher(spec);
        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Position(x, y);
        }
        return null;
    }

    public long getTokensForPrize() {
        // ax + by = e
        // cx + dy = f

        // XdeA * inputA + XdeB * inputB = XdeP
        // YdeA * inputA + YdeB * inputB = YdeP

        // (ed - bf) /(ad - bc)
        long inputA = (prize.getX() * buttonB.getY() - prize.getY() * buttonB.getX()) / (buttonA.getX() * buttonB.getY() - buttonA.getY() * buttonB.getX());
        long inputB = (prize.getX() * buttonA.getY() - prize.getY() * buttonA.getX()) / (buttonB.getX() * buttonA.getY() - buttonB.getY() * buttonA.getX());

        if (checkInput(inputA, inputB)) {
            return inputA * 3 + inputB;
        } else {
            return 0;
        }
    }

    private boolean checkInput(long inputA, long inputB) {
        long x = inputA * buttonA.getX() + inputB * buttonB.getX();
        long y = inputA * buttonA.getY() + inputB * buttonB.getY();
        return x == prize.getX() && y == prize.getY();
    }
}
