package lol.koblizek.juix.core.bootstrap.events;

import lol.koblizek.juix.api.event.Event;
import lol.koblizek.juix.core.Application;
import lombok.Getter;

@Getter
public class ProcessObtainMessageEvent extends Event {
    private final Application<?> app;

    public ProcessObtainMessageEvent(Application<?> app) {
        this.app = app;
    }
}
