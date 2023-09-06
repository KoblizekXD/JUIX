package lol.koblizek.juix.api.window;

import com.microsoft.win32.MSG;
import com.microsoft.win32.POINT;
import com.microsoft.win32.RECT;
import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.core.IDisposable;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;
import lombok.extern.log4j.Log4j2;

import java.lang.foreign.Arena;

import static com.microsoft.win32.windows_h_14.GetLastError;
import static com.microsoft.win32.windows_h_16.*;
import static com.microsoft.win32.windows_h_17.GetClientRect;
import static com.microsoft.win32.windows_h_17.GetWindowRect;
import static com.microsoft.win32.windows_h_34.WS_OVERLAPPEDWINDOW;
import static java.lang.foreign.MemorySegment.NULL;

/**
 * Main window classes used for displaying content on the screen.
 */
@Log4j2
public class Window implements IDisposable, IResizable {
    private final Arena arena;
    private final WindowClass windowClass;
    private final Handle handle;

    public Window(Arena arena, WindowClass windowClass) {
        this.arena = arena;
        this.windowClass = windowClass;
        this.handle = create();
    }
    private Handle create() {
        var handle = CreateWindowExA(
                0,
                windowClass.getClassName(),
                windowClass.getClassName(),
                WS_OVERLAPPEDWINDOW(),
                100, 100,
                300, 300,
                NULL,
                NULL,
                windowClass.getHInstance(),
                NULL
        );
        if (handle == NULL) {
            BootstrapLauncher.writeWin32Error(GetLastError());
            throw new RuntimeException();
        } else {
            return new Handle(handle);
        }
    }
    public void show() {
        ShowWindow(handle.getHandleAsMemory(), 1);
        var msg = MSG.allocate(arena);
        while (GetMessageW(msg, NULL, 0, 0) > 0) {
            TranslateMessage(msg);
            DispatchMessageA(msg);
        }
    }
    @Override
    public void dispose() {
        int code = DestroyWindow(handle.getHandleAsMemory());
        if (code == 0) {
            BootstrapLauncher.writeWin32Error(GetLastError());
        }
    }

    @Override
    public void resize(int newWidth, int newHeight) {
        var rcClient = RECT.allocate(arena);
        var rcWind = RECT.allocate(arena);
        var ptDiff = POINT.allocate(arena);
        GetClientRect(handle.getHandleAsMemory(), rcClient);
        GetWindowRect(handle.getHandleAsMemory(), rcWind);
        POINT.x$set(ptDiff, (RECT.right$get(rcWind) - RECT.left$get(rcWind)) - RECT.right$get(rcClient));
        POINT.y$set(ptDiff, (RECT.bottom$get(rcWind) - RECT.top$get(rcWind)) - RECT.bottom$get(rcClient));
        MoveWindow(handle.getHandleAsMemory(), RECT.left$get(rcWind), RECT.top$get(rcWind), newWidth + POINT.x$get(ptDiff), newHeight + POINT.y$get(ptDiff), 1);
    }
}
