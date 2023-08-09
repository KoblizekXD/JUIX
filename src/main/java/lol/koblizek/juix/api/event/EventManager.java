package lol.koblizek.juix.api.event;

import lol.koblizek.juix.api.internal.Internal;
import lol.koblizek.juix.core.reflect.Reflection;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
public final class EventManager {

    private EventManager() {}

    public static void invoke(Class<? extends Event> e) {
        var responses = Reflection.REFLECTIONS.getMethodsWithParameter(e);
        for (Method response : responses) {
            try {
                response.trySetAccessible();
                response.invoke(null, e.newInstance());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
                log.error("Error invoking response method: {}", ex.getMessage());
            }
        }
    }
}
