package backend.exception;

public class InvalidName extends Exception {
    public InvalidName() {
        super("Duplicated name");
    }
}
