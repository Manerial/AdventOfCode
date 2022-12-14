package adventofcode.aoc2022.day14;


public enum Block {
    ROCK('#'), SAND('.'), AIR(' ');
    private final char blockName;

    Block(char blockName) {
        this.blockName = blockName;
    }

    @Override
    public String toString(){
        return blockName + "";
    }
}
