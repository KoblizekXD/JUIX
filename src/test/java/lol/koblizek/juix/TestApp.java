package lol.koblizek.juix;

import lol.koblizek.juix.api.window.Window;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;
import org.junit.jupiter.api.Test;

public class TestApp extends Application<Window> {
    @Override
    public void onInitialize(Window disposable) {

    }
    @Test
    void testLaunch() {
        BootstrapLauncher.inCustomMain(TestApp.class);
    }
}
