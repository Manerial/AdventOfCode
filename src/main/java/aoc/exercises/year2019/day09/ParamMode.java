package aoc.exercises.year2019.day09;

public enum ParamMode {
    POSITION,
    IMMEDIATE,
    RELATIVE;

    public static ParamMode fromValue(int value) {
        return switch (value) {
            case 0 -> POSITION;
            case 1 -> IMMEDIATE;
            case 2 -> RELATIVE;
            default -> null;
        };
    }
}
