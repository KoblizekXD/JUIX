package lol.koblizek.juix.core.error;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
        super();
    }
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
