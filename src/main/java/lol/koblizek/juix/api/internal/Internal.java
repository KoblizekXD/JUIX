package lol.koblizek.juix.api.internal;

import lol.koblizek.juix.core.error.UnsatisfiedInternalAccessException;

/**
 * Internal API was added, so we can store bindings classes(a.k.a. internal classes) in classes used by consumer.
 * Before running any method in this class, it will check if it was called in sufficient package, if it was not,
 * throws {@link UnsatisfiedInternalAccessException}, otherwise continues with desired action.
 *
 * @param <T> type used as variable
 * @see #check()
 */
public class Internal<T> {
    private T value;

    public Internal(T value) {
        this.value = value;
    }

    /**
     * Runs sufficient check and returns value of T if succeeded
     * @throws UnsatisfiedInternalAccessException if check failed
     * @return value of T
     */
    public T get() {
        var trace = new Throwable().getStackTrace();
        var element = trace[trace.length-1];
        if (!element.getClassName().startsWith("lol.koblizek.juix")) {
            throw new UnsatisfiedInternalAccessException();
        }
        return value;
    }
    /**
     * Runs sufficient check and sets the value of T if succeeded
     * @throws UnsatisfiedInternalAccessException if check failed
     */
    public void set(T val) {
        var trace = new Throwable().getStackTrace();
        var element = trace[trace.length-1];
        if (!element.getClassName().startsWith("lol.koblizek.juix")) {
            throw new UnsatisfiedInternalAccessException();
        }
        value = val;
    }
    /**
     * Used for checking if invocation took place in correct package
     * @throws UnsatisfiedInternalAccessException if check failed
     */
    public static void check() {
        var trace = new Throwable().getStackTrace();
        var element = trace[trace.length-1];
        if (!element.getClassName().startsWith("lol.koblizek.juix")) {
            throw new UnsatisfiedInternalAccessException();
        }
    }
}
