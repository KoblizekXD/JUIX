package lol.koblizek.juix.api.libload;

import lol.koblizek.juix.core.Application;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

/**
 * Open API for loading native libraries
 */
@Getter
@Log4j2
public final class LibLoad {
    private final Class<? extends Application> app;
    private final boolean present;
    private String[] system;

    /**
     * @param app class corresponding to application being launched
     * @param present whether is the annotation present on the application class
     */
    public LibLoad(Class<? extends Application> app, boolean present) {
        this.app = app;
        this.present = present;
    }

    public static @NotNull LibLoad inType(@NotNull Class<? extends Application> app) {
        if (app.isAnnotationPresent(Load.class)) {
            return new LibLoad(app, true);
        } else return new LibLoad(app, false);
    }
    private String[] getLoadingLibraries() {
        if (!present) {
            log.warn("Attempted to call method \"getLoadingLibraries\" but annotation is not present!");
            return new String[0];
        } else {
            return app.getAnnotation(Load.class).value();
        }
    }
    public void load(String name) {
        System.loadLibrary(name);
    }
    public void system(String... names) {
        this.system = names;
    }

    /**
     * Loads all system and user-defined native libraries, if the loading failed application will have status FAILED,
     * SUCCESS otherwise.
     */
    public void loadAll() {
        var libraries = getLoadingLibraries();
        log.info("Native libraries preload. Found {} system libraries and {} user-defined", system.length, libraries.length);
        log.info("+---system libraries:");
        for (String lib : system) {
            try {
                System.loadLibrary(lib);
                log.info("\t+---{}(SUCCESS)", lib);
            } catch (Exception e) {
                log.info("\t+---{}(FAILED)", lib);
            }
        }
        if (!present) return;
        log.info("+---user-defined libraries:");
        for (String lib : getLoadingLibraries()) {
            try {
                System.loadLibrary(lib);
                log.info("\t+---{}(SUCCESS)", lib);
            } catch (Exception e) {
                log.info("\t+---{}(FAILED)", lib);
            }
        }
    }
}
