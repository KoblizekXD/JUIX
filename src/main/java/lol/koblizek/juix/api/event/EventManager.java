package lol.koblizek.juix.api.event;

import lol.koblizek.juix.core.reflect.Reflection;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
public final class EventManager {

    private EventManager() {}

    /**
     * Invokes an event, all handlers(method which has 1 parameter - instance of the event) are invoked in order returned by Reflections library
     *
     * @param e event to be invoked
     */
    public static void invoke(Event e) {
        var responses = Reflection.REFLECTIONS.getMethodsWithParameter(e.getClass());
        for (Method response : responses) {
            try {
                response.trySetAccessible();
                response.invoke(null, e);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                log.error("Error invoking response method: {}", ex.getMessage());
            }
        }
    }
}
