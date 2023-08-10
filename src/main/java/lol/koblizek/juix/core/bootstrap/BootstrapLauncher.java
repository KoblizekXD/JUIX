package lol.koblizek.juix.core.bootstrap;

import com.microsoft.win32.WNDCLASSA;
import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.api.WindowProcess;
import lol.koblizek.juix.api.event.EventManager;
import lol.koblizek.juix.api.libload.LibLoad;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.bootstrap.events.PreInitializationEvent;
import lol.koblizek.juix.core.error.ApplicationNotFoundException;
import lol.koblizek.juix.core.reflect.Reflection;
import lombok.extern.log4j.Log4j2;

import java.lang.foreign.Arena;

import static com.microsoft.win32.windows_h_14.GetLastError;

@Log4j2
public final class BootstrapLauncher {
    public static void writeWin32Error(int code) {
        log.error("Failed to dispose window: {}", code);
        log.error("Please refer to Microsoft documentation for further details");
    }
    @SuppressWarnings("deprecation")
    private static void launch() {
        try {
            log.warn("Using deprecated reflection features: migration required in future");
            var type = Reflection.getApplicationClasses().stream().findFirst();
            if (type.isEmpty()) {
                log.fatal("No applications found, stopping...", new ApplicationNotFoundException());
                System.exit(1);
            }
            var app = Reflection.of(type.get());
            var libload = LibLoad.inType(type.get());
            libload.system("kernel32", "user32");
            libload.loadAll();
            app.setNewInstance();
            EventManager.invoke(new PreInitializationEvent((Application<?>) app.getInstance()));
            app.getMethod("onInitialize").invoke(app.getInstance(), app.getType().newInstance());
            log.info("Initializing internal api...");
            try (Arena arena = Arena.openConfined()) {
                WindowClass windowClass = new WindowClass(arena)
                        .setClassName(((Application<?>) app.getInstance()).getName())
                        .setWindowProcess(new WindowProcess());
                log.info("Registering window class...");
                windowClass.register();
                app.getAsApplication().internalWindowClass.set(windowClass);
            }
        } catch (Exception e) {
            log.fatal("Application failed to run with exception: ", e); 
        }
    }

    public static void main(String[] args) {
        launch();
    }
    public static void inCustomMain() {
        launch();
    }
}
