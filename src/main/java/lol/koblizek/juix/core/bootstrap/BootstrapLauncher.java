package lol.koblizek.juix.core.bootstrap;

import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.api.WindowProcess;
import lol.koblizek.juix.api.event.EventManager;
import lol.koblizek.juix.api.libload.LibLoad;
import lol.koblizek.juix.api.window.Window;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.IDisposable;
import lol.koblizek.juix.core.bootstrap.events.PreInitializationEvent;
import lol.koblizek.juix.core.error.UnspecifiedEntrypointException;
import lol.koblizek.juix.core.resource.ResourceManager;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.util.Properties;

@Log4j2
public final class BootstrapLauncher {
    public static void writeWin32Error(int code) {
        log.error("Failed to dispose window: {}", code);
        log.error("Please refer to Microsoft documentation for further details");
    }
    @SuppressWarnings("deprecation")
    private static void launch(Class<? extends Application<? extends IDisposable>> app) {
        try {
            ResourceManager manager = ResourceManager.getInstance();
            Class<? extends Application<? extends IDisposable>> type = null;
            if (app == null && manager.propertiesExist()) {
                var properties = manager.getProperties();
                try {
                    type = (Class<? extends Application<? extends IDisposable>>) Class.forName(properties.getProperty("entrypoint"));
                } catch (ClassNotFoundException e) {
                    log.fatal("Couldn't find class: {}", properties.getProperty("entrypoint"));
                }
            } else if (app != null) {
                type = app;
            } else {
                log.fatal("Property file not found", new UnspecifiedEntrypointException());
                System.exit(1);
            }
            var libload = LibLoad.inType(type);
            libload.system("kernel32", "user32");
            libload.loadAll();
            Application application = type.newInstance();
            EventManager.invoke(new PreInitializationEvent(application));
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
    @Deprecated
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
        launch(null);
    }
    public static void inCustomMain(Class<? extends Application<? extends IDisposable>> app) {
        launch(app);
    }
}
