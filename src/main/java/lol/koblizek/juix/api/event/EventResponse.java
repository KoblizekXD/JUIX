package lol.koblizek.juix.api.event;

import lol.koblizek.juix.core.util.Unused;

@Unused
public final class EventResponse<T extends Event> {
    private final Status status;
    private T responded;

    private EventResponse(Status status) {
        this.status = status;
    }
    public EventResponse<T> as(T responded) {
        this.responded = responded;
        return this;
    }
    public static <T extends Event> EventResponse<T> succeed() {
        return new EventResponse<>(Status.SUCCESS);
    }
    public static <T extends Event> EventResponse<T> cancel() {
        return new EventResponse<>(Status.CANCELLATION);
    }
    private enum Status {
        FAILURE,
        SUCCESS,
        CANCELLATION
    }
}
