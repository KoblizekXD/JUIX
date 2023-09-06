package lol.koblizek.juix;

import lol.koblizek.juix.api.window.Window;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.bootstrap.ApplicationConfiguration;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;
import org.junit.jupiter.api.Test;

import static com.microsoft.win32.windows_h_17.SystemParametersInfoA;
import static com.microsoft.win32.windows_h_4.WM_SETFONT;
import static com.microsoft.win32.windows_h_6.SPI_GETNONCLIENTMETRICS;

@ApplicationConfiguration(name = "Test app")
public class TestApp extends Application<Window> {
    @Override
    public void onInitialize(Window disposable) {
        disposable.resize(700, 200);
    }
    @Test
    void testLaunch() {
        BootstrapLauncher.inCustomMain(TestApp.class);
    }
}
