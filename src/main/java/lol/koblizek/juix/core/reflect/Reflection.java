package lol.koblizek.juix.core.reflect;

import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.IDisposable;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

@Log4j2
public final class Reflection {
    public static final Reflections REFLECTIONS
            = new Reflections();
    private final Class<?> type;
    @Getter
    private Object instance;

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
    public void setNewInstance() {
        try {
            instance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Failed to instantiate type: {}", e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Class<? extends IDisposable> getType() {
        log.warn("getType may produce errors if used incorrectly");
        return (Class<? extends IDisposable>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
    public <T extends IDisposable> Application<T> getAsApplication() {
        return (Application<T>) getInstance();
    }
}
