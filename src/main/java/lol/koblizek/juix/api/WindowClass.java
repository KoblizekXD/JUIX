package lol.koblizek.juix.api;

import com.microsoft.win32.WNDCLASSA;
import com.microsoft.win32.WNDPROC;
import lol.koblizek.juix.api.internal.Internal;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

import static com.microsoft.win32.windows_h_14.GetLastError;
import static com.microsoft.win32.windows_h_15.GetModuleHandleA;
import static com.microsoft.win32.windows_h_16.RegisterClassA;
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
    public void register() {
        Internal.check();
        short atom = RegisterClassA(wndClassA);
        if (atom == 0) BootstrapLauncher.writeWin32Error(GetLastError());
    }

    public MemorySegment getClassName() {
        return WNDCLASSA.lpszClassName$get(wndClassA);
    }
    public MemorySegment getHInstance() {
        return WNDCLASSA.hInstance$get(wndClassA);
    }
}
