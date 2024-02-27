package utilities;

import java.util.List;

public abstract class AbstractInputParser<T> {
    protected List<String> inputList;

    protected AbstractInputParser(List<String> inputList) {
        this.inputList = inputList;
    }

    public abstract T parseInput();
}
