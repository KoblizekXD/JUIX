package lol.koblizek.juix.api.window;

import lol.koblizek.juix.core.IDisposable;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;
import lombok.extern.log4j.Log4j2;

import static com.microsoft.win32.windows_h_14.GetLastError;
import static com.microsoft.win32.windows_h_16.DestroyWindow;

@Log4j2
public class Window implements IDisposable {
    private final Handle handle;

    public Window(Handle handle) {
        this.handle = handle;
    }
    @Override
    public void dispose() {
        int code = DestroyWindow(handle.getHandleAsMemory());
        if (code == 0) {
            BootstrapLauncher.writeWin32Error(GetLastError());
        }
    }
}
