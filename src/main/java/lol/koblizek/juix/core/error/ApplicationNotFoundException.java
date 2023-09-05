package lol.koblizek.juix.core.error;

/**
 * Used when class is specified in property file but not found
 */
public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
        super();
    }
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
