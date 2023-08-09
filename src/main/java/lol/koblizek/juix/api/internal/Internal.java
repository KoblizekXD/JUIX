package lol.koblizek.juix.api.internal;

public class Internal<T> {
    private final T value;

    public Internal(T value) {
        this.value = value;
    }
    public T get() {
        Throwable throwable = new Throwable();
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            System.out.println(stackTraceElement.getClassName());
        }
        return value;
    }
}
