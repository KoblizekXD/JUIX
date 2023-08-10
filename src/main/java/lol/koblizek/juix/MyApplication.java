package lol.koblizek.juix;

import lol.koblizek.juix.api.window.Window;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.bootstrap.BootstrapLauncher;

public class MyApplication extends Application<Window> {
    @Override
    public void onInitialize(Window disposable) {

    }

    public static void main(String[] args) {
        BootstrapLauncher.inCustomMain();
    }
}
