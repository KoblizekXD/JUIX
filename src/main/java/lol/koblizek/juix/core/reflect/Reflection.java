package lol.koblizek.juix.core.reflect;

import lol.koblizek.juix.core.Application;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

@Log4j2
public final class Reflection {
    public static final Reflections REFLECTIONS
            = new Reflections();
    private final Class<?> type;

    private Reflection(Class<?> type) {
        this.type = type;
    }

    public static Set<Class<? extends Application>> getApplicationClasses() {
        return REFLECTIONS.getSubTypesOf(Application.class);
    }
    public static Reflection of(Class<?> type) {
        return new Reflection(type);
    }

    public Method getMethod(String name, Class<?> ... params) {
        try {
            return type.getDeclaredMethod(name, params);
        } catch (NoSuchMethodException e) {
            log.error("Failed to get declared method: {}, null will be returned", name);
            return null;
        }
    }
}
