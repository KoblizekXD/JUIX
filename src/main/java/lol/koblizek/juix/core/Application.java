package lol.koblizek.juix.core;

public abstract class Application<T> {
    public abstract void onInitialize(T disposable);
}
