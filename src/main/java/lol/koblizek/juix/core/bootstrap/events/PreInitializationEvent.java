package lol.koblizek.juix.core.bootstrap.events;

import lol.koblizek.juix.api.event.Event;
import lol.koblizek.juix.core.Application;
import lombok.Getter;

@Getter
public class PreInitializationEvent extends Event {
    private final Application<?> app;

    public PreInitializationEvent(Application<?> app) {
        this.app = app;
    }

}
