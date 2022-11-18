package backend.errors;

public class InvalidName extends Exception {
    public InvalidName() {
        super("Duplicated name");
    }
}
