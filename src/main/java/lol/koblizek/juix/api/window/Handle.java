package lol.koblizek.juix.api.window;

import lol.koblizek.juix.api.internal.Internal;

import java.lang.foreign.MemorySegment;

/**
 * Handle class mainly holds object of {@link MemorySegment }, which represents HANDLE, created by
 * BootstrapLauncher
 */
public final class Handle {
    private final MemorySegment hWnd;

    /**
     * Constructs a new Handle instance
     *
     * @param hWnd window handle memory segment
     */
    public Handle(MemorySegment hWnd) {
        Internal.check();
        this.hWnd = hWnd;
    }

    public MemorySegment getHandleAsMemory() {
        Internal.check();
        return hWnd;
    }
}
