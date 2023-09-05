package lol.koblizek.juix.core.bootstrap.events;

import lol.koblizek.juix.api.event.Event;
import lombok.Getter;

@Getter
public class ApplicationCrashEvent extends Event {
    private final Class<?> app;

    public ApplicationCrashEvent(Class<?> app) {
        this.app = app;
    }
}
