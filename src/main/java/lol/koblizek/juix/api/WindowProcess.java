package lol.koblizek.juix.api;

import com.microsoft.win32.WNDPROC;

import java.lang.foreign.MemorySegment;

import static com.microsoft.win32.windows_h_16.DefWindowProcA;

public final class WindowProcess implements WNDPROC {
    @Override
    public long apply(MemorySegment hWnd, int uMsg, long wParam, long lParam) {
        return DefWindowProcA(hWnd, uMsg, wParam, lParam);
    }
}
