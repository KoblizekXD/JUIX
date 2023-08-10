package lol.koblizek.juix.api.window;

import com.microsoft.win32.MSG;
import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.core.IDisposable;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;
import lombok.extern.log4j.Log4j2;

import java.lang.foreign.Arena;

import static com.microsoft.win32.windows_h_14.GetLastError;
import static com.microsoft.win32.windows_h_16.*;
import static com.microsoft.win32.windows_h_34.WS_OVERLAPPEDWINDOW;
import static com.microsoft.win32.windows_h_34.WS_VISIBLE;
import static java.lang.foreign.MemorySegment.NULL;

/**
 * Main window classes used for displaying content on the screen.
 */
@Log4j2
public class Window implements IDisposable {
    private final WindowClass windowClass;
    private final Handle handle;

    public Window(WindowClass windowClass) {
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
    public void show(Arena arena) {
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
}
