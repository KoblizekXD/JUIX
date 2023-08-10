package lol.koblizek.juix.api.window;

import lol.koblizek.juix.api.internal.Internal;

import java.lang.foreign.MemorySegment;

public final class Handle {
    private final MemorySegment hWnd;

    public Handle(MemorySegment hWnd) {
        Internal.check();
        this.hWnd = hWnd;
    }

    public MemorySegment getHandleAsMemory() {
        Internal.check();
        return hWnd;
    }
}
