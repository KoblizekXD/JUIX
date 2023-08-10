package lol.koblizek.juix.api.window;

import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.core.IDisposable;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;
import lombok.extern.log4j.Log4j2;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static com.microsoft.win32.windows_h_14.GetLastError;
import static com.microsoft.win32.windows_h_16.CreateWindowExA;
import static com.microsoft.win32.windows_h_16.DestroyWindow;
import static com.microsoft.win32.windows_h_34.WS_OVERLAPPEDWINDOW;

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
                MemorySegment.NULL,
                MemorySegment.NULL,
                windowClass.getHInstance(),
                MemorySegment.NULL
        );
        if (handle == MemorySegment.NULL) {
            BootstrapLauncher.writeWin32Error(GetLastError());
            throw new RuntimeException();
        } else {
            return new Handle(handle);
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
