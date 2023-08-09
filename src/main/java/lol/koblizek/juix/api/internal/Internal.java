package lol.koblizek.juix.api.internal;

import lol.koblizek.juix.core.error.UnsatisfiedInternalAccessException;

public class Internal<T> {
    private T value;

    public Internal(T value) {
        this.value = value;
    }
    public T get() {
        var trace = new Throwable().getStackTrace();
        var element = trace[trace.length-1];
        if (!element.getClassName().startsWith("lol.koblizek.juix")) {
            throw new UnsatisfiedInternalAccessException();
        }
        return value;
    }
    public void set(T val) {
        var trace = new Throwable().getStackTrace();
        var element = trace[trace.length-1];
        if (!element.getClassName().startsWith("lol.koblizek.juix")) {
            throw new UnsatisfiedInternalAccessException();
        }
        value = val;
    }
    public static void check() {
        var trace = new Throwable().getStackTrace();
        var element = trace[trace.length-1];
        if (!element.getClassName().startsWith("lol.koblizek.juix")) {
            throw new UnsatisfiedInternalAccessException();
        }
    }
}
