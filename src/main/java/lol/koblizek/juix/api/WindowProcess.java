package lol.koblizek.juix.api;

import com.microsoft.win32.WNDPROC;

import java.lang.foreign.MemorySegment;

import static com.microsoft.win32.windows_h_16.DefWindowProcA;
import static com.microsoft.win32.windows_h_16.PostQuitMessage;

public final class WindowProcess implements WNDPROC {
    @Override
    public long apply(MemorySegment hWnd, int uMsg, long wParam, long lParam) {
        switch (uMsg) {
            case 0x0002 -> {
                PostQuitMessage(0);
                return 0;
            }
        }
        return DefWindowProcA(hWnd, uMsg, wParam, lParam);
    }
}
