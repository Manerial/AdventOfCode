package utilities;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public abstract class AbstractInputParser<T> {
    protected List<String> inputList;

    protected AbstractInputParser(List<String> inputList) {
        this.inputList = inputList;
    }

    public T parseInput() {
        throw new NotImplementedException();
    }
}
