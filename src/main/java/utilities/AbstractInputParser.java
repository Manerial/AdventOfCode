package utilities;

import java.util.List;

public abstract class AbstractInputParser {
    protected List<String> inputList;

    protected AbstractInputParser(List<String> inputList) {
        this.inputList = inputList;
    }
}
