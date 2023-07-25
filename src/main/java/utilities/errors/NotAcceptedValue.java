package utilities.errors;

public class NotAcceptedValue extends IllegalArgumentException {
    public NotAcceptedValue(Object object) {
        super("The value " + object.toString() + " is not accepted.");
    }
}
