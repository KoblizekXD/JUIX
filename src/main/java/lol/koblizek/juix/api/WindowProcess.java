package lol.koblizek.juix.api;

import com.microsoft.win32.WNDPROC;
import lol.koblizek.juix.api.event.EventManager;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.bootstrap.events.ProcessObtainMessageEvent;
import lombok.Getter;

import java.lang.foreign.MemorySegment;

import static com.microsoft.win32.windows_h_16.DefWindowProcA;
import static com.microsoft.win32.windows_h_16.PostQuitMessage;

@Getter
public final class WindowProcess implements WNDPROC {
    private final Application<?> app;

    public WindowProcess(Application<?> app) {
        this.app = app;
    }
    @Override
    public long apply(MemorySegment hWnd, int uMsg, long wParam, long lParam) {
        EventManager.invoke(new ProcessObtainMessageEvent(app));
        if (uMsg == com.microsoft.win32.windows_h_4.WM_DESTROY()) {
            PostQuitMessage(0);
            return 0;
        }
        return DefWindowProcA(hWnd, uMsg, wParam, lParam);
    }
}
