package lol.koblizek.juix.core.error;

/**
 * Thrown when unprivileged method attempts to call method from protected class
 */
public class UnsatisfiedInternalAccessException extends RuntimeException {
    public UnsatisfiedInternalAccessException() {
        super();
    }
    public UnsatisfiedInternalAccessException(String msg) {
        super(msg);
    }
}
