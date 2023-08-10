package lol.koblizek.juix.core.reflect;

import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.IDisposable;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Set;

@Log4j2
public final class Reflection {
    public static final Reflections REFLECTIONS
            = new Reflections();

    @SuppressWarnings("unchecked")
    public static Class<? extends IDisposable> getType(Application<?> application) {
        log.warn("getType may produce errors if used incorrectly");
        return (Class<? extends IDisposable>) ((ParameterizedType) application.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public static Object newInstance(Class<?> type, Object... objects) {
        try {
            return type.getDeclaredConstructor(Arrays.stream(objects).map(obj -> obj.getClass())
                    .toArray(Class[]::new)).newInstance(objects);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
