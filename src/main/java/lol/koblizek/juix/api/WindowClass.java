package lol.koblizek.juix.api;

import com.microsoft.win32.WNDCLASSA;
import com.microsoft.win32.WNDPROC;
import lol.koblizek.juix.api.internal.Internal;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

import static com.microsoft.win32.windows_h_15.GetModuleHandleA;
import static java.lang.foreign.MemorySegment.NULL;

public final class WindowClass {
    private final MemorySegment wndClassA;
    private final Arena arena;

    public WindowClass(Arena arena) {
        Internal.check();
        this.arena = arena;
        wndClassA = WNDCLASSA.allocate(arena);
    }
    public WindowClass setClassName(String name) {
        WNDCLASSA.lpszClassName$set(wndClassA, arena.allocateUtf8String(name));
        return this;
    }
    public WindowClass setWindowProcess(WNDPROC proc) {
        WNDCLASSA.lpfnWndProc$set(wndClassA, WNDPROC.allocate(proc, SegmentScope.global()));
        return this;
    }
    public WindowClass setInstance() {
        WNDCLASSA.hInstance$set(wndClassA, GetModuleHandleA(NULL));
        return this;
    }
}
