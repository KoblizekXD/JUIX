package lol.koblizek.juix.api.event;

import lol.koblizek.juix.api.internal.Internal;
import lol.koblizek.juix.core.reflect.Reflection;
import lombok.extern.log4j.Log4j2;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
public final class EventManager {

    public EventManager() {
        Internal.check();
    }
    public void invoke(Class<? extends Event> e, Object ... params) {
        var responses = Reflection.REFLECTIONS.getMethodsWithParameter(e);
        for (Method response : responses) {
            try {
                response.trySetAccessible();
                response.invoke(null, params);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                log.error("Error invoking response method: {}", ex.getMessage());
            }
        }
    }
}
