package utilities.input_parser;

import java.util.List;

public abstract class AbstractInputParser<T> implements InputParser<T> {
    protected List<String> inputList;

    protected AbstractInputParser(List<String> inputList) {
        this.inputList = inputList;
    }
}
