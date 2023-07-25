package aoc.exercises.year2015.day03;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import aoc.common_objects.RoundDeque;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private RoundDeque<Santa> santaTeam;
    private List<Position> housesVisited;

    @Override
    public void run() {
        String input = inputList.get(0);

        initPositions(1);
        visitAllHouses(input);
        solution1 = housesVisited.size();

        initPositions(2);
        visitAllHouses(input);
        solution2 = housesVisited.size();
    }

    /**
     * Initializes the positions the visited houses and the santa team.
     *
     * @param santas : the number of santas to initialize
     */
    private void initPositions(int santas) {
        Position initialPosition = new Position(0, 0);
        santaTeam = new RoundDeque<>();
        housesVisited = new ArrayList<>();
        housesVisited.add(initialPosition);

        for (int i = 0; i < santas; i++) {
            this.santaTeam.add(new Santa(initialPosition));
        }
    }

    /**
     * For each direction in the input, the current santa move then visit the house.
     *
     * @param input : the list of movements of the santas
     */
    private void visitAllHouses(String input) {
        for (int i = 0; i < input.length(); i++) {
            Direction direction = Direction.charToDirection(input.charAt(i));
            visitHouse(direction, santaTeam.getFirst());
            santaTeam.rotate(1);
        }
    }

    /**
     * Move the santa to the next house, depending on the cardinal direction.
     *
     * @param cardinal : the cardinal direction of the santa
     * @param santa    : the santa to move
     */
    private void visitHouse(Direction cardinal, Santa santa) {
        Position newPosition = new Position(santa.getPosition());
        switch (cardinal) {
            case NORTH -> newPosition.incY();
            case SOUTH -> newPosition.decY();
            case EAST -> newPosition.incX();
            case WEST -> newPosition.decX();
        }

        if (!housesVisited.contains(newPosition)) {
            housesVisited.add(newPosition);
        }
        santa.setPosition(newPosition);
    }
}