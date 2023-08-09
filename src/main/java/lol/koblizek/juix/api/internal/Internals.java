package lol.koblizek.juix.api.internal;


import java.util.HashMap;
import java.util.Map;

/**
 * Collection of internal packages, used for non-consumer purposes
 */
public class Internals<T> {
    private final Map<String, Internal<T>> internals;

    public Internals() {
        Internal.check();
        internals = new HashMap<>();
    }
    public Internal<T> get(String name) {
        Internal.check();
        return internals.get(name);
    }
    public void add(String name, Internal<T> internal) {
        Internal.check();
        internals.put(name, internal);
    }
}
