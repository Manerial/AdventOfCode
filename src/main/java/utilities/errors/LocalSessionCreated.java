package utilities.errors;

import java.io.FileNotFoundException;

public class LocalSessionCreated extends FileNotFoundException {
    public LocalSessionCreated() {
        super("Your localSession file has been created in [PROJECT]\\resources\\, please fill it.");
    }
}
