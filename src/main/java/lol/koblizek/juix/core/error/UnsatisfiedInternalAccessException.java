package lol.koblizek.juix.core.error;

public class UnsatisfiedInternalAccessException extends RuntimeException {
    public UnsatisfiedInternalAccessException() {
        super();
    }
    public UnsatisfiedInternalAccessException(String msg) {
        super(msg);
    }
}
