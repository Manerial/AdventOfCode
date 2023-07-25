package utilities.errors;

import java.util.NoSuchElementException;

public class NoSuchElementInListException extends NoSuchElementException {
    public NoSuchElementInListException() {
        super("The list contain no element matching the selection criteria");
    }
}
