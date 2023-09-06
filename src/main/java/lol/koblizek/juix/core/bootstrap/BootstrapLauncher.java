package lol.koblizek.juix.core.bootstrap;

import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.api.WindowProcess;
import lol.koblizek.juix.api.event.EventManager;
import lol.koblizek.juix.api.libload.LibLoad;
import lol.koblizek.juix.api.window.Window;
import lol.koblizek.juix.core.Application;
import lol.koblizek.juix.core.IDisposable;
import lol.koblizek.juix.core.bootstrap.events.ApplicationCrashEvent;
import lol.koblizek.juix.core.bootstrap.events.PreInitializationEvent;
import lol.koblizek.juix.core.error.UnspecifiedEntrypointException;
import lol.koblizek.juix.core.resource.ResourceManager;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.util.Properties;

@Log4j2
public final class BootstrapLauncher {
    /**
     * Writes a simple error message for an internal win32 api error,
     * the full list of errors is available on Microsoft site(just google "win32 api error codes bruh")
     *
     * @param code error code to write out
     */
    public static void writeWin32Error(int code) {
        log.error("An error occurred, code: {}", code);
        log.error("Please refer to Microsoft documentation for further details");
    }

    /**
     * Attempts to launch a main application runtime, any error will result to automatic application termination.
     * Loads all native libraries using LibLoad api and uses experimental features of JDK 21.<br>
     * Standard launch procedure is following:
     * <ul>
     *     <li>Find a class corresponding to launch options</li>
     *     <li>Load all native libraries(user included and system)</li>
     *     <li>Create new instance of the application</li>
     *     <li>Register the window class</li>
     *     <li>Display the window</li>
     * </ul>
     *
     * @param app Application class - can be null if you want to find it automatically from property file
     */
    @SuppressWarnings("deprecation")
    private static void launch(Class<? extends Application<? extends IDisposable>> app) {
        try {
            log.info("Resource manager v2: initalizing");
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
                        .setWindowProcess(new WindowProcess(application));
                log.info("Registering window class...");
                windowClass.register();
                Window window = new Window(windowClass);
                application.internalWindowClass.set(windowClass);
                application.internalWindow.set(window);
                application.onInitialize(window);
                window.show(arena);
            }
        } catch (Exception e) {
            EventManager.invoke(new ApplicationCrashEvent(app));
            log.fatal("Application failed to run with exception: ", e); 
        }
    }

    /**
     * Loads main property file and gets the value of entrypoint field
     *
     * @deprecated Deprecated since the come of new Resource api({@link lol.koblizek.juix.core.resource.ResourceManager ResourceManager})
     * @return value of "entrypoint" property inside "juix.properties" file
     */
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

    /**
     * Launches the app, please don't use it as normal library user
     *
     * @param args program args(unused)
     */
    public static void main(String[] args) {
        launch(null);
    }

    /**
     * Launches the application in specific method(without the need of special launch configuration)
     *
     * @param app Application to launch
     */
    public static void inCustomMain(Class<? extends Application<? extends IDisposable>> app) {
        launch(app);
    }
}
