package utilities.input_parser;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public abstract class AbstractInputParser<T> implements InputParserInterface<T> {
    protected List<String> inputList;

    protected AbstractInputParser(List<String> inputList) {
        this.inputList = inputList;
    }

    public T parseInput() {
        throw new NotImplementedException();
    }
}
