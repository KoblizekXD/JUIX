package lol.koblizek.juix.core.bootstrap;

import com.microsoft.win32.WNDCLASSA;
import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.api.WindowProcess;
import lol.koblizek.juix.api.event.EventManager;
import lol.koblizek.juix.api.libload.LibLoad;
import lol.koblizek.juix.api.window.Window;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.IDisposable;
import lol.koblizek.juix.core.bootstrap.events.PreInitializationEvent;
import lol.koblizek.juix.core.error.ApplicationNotFoundException;
import lol.koblizek.juix.core.reflect.Reflection;
import lol.koblizek.juix.core.resource.ResourceManager;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.util.Properties;

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
            var type = (Class<? extends Application<? extends IDisposable>>) Class.forName(loadProperties());
            var libload = LibLoad.inType(type);
            libload.system("kernel32", "user32");
            libload.loadAll();
            Application application = type.newInstance();
            EventManager.invoke(new PreInitializationEvent(application));
            //app.getMethod("onInitialize").invoke(app.getInstance(), app.getType().newInstance());
            log.info("Initializing internal api...");
            try (Arena arena = Arena.openConfined()) {
                WindowClass windowClass = new WindowClass(arena)
                        .setClassName(application.getName())
                        .setWindowProcess(new WindowProcess());
                log.info("Registering window class...");
                windowClass.register();
                Window window = new Window(windowClass);
                application.internalWindowClass.set(windowClass);
                application.internalWindow.set(window);
                application.onInitialize(window);
                window.show(arena);
            }
        } catch (Exception e) {
            log.fatal("Application failed to run with exception: ", e); 
        }
    }
    public static String loadProperties() {
        var props = ResourceManager.getInstance()
                .getResource("/juix.properties");
        Properties properties = new Properties();
        try {
            properties.load(props.stream());
            return properties.getProperty("entrypoint");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
    public static void inCustomMain() {
        launch();
    }
}
