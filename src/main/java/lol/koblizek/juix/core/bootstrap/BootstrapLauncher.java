package lol.koblizek.juix.core.bootstrap;

import lol.koblizek.juix.api.internal.Internal;
import lol.koblizek.juix.api.libload.LibLoad;
import lol.koblizek.juix.core.error.ApplicationNotFoundException;
import lol.koblizek.juix.core.reflect.Reflection;
import lombok.extern.log4j.Log4j2;

import java.lang.foreign.Arena;

@Log4j2
public final class BootstrapLauncher {
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
            app.getMethod("onInitialize").invoke(type.get().newInstance(), app.getType().newInstance());
            log.info("Initializing internal api...");
            try (Arena arena = Arena.openConfined()) {

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
