package lol.koblizek.juix.core;

import lol.koblizek.juix.api.WindowClass;
import lol.koblizek.juix.api.internal.Internal;
import lol.koblizek.juix.core.bootstrap.ApplicationConfiguration;

public abstract class Application<T extends IDisposable> {
    public final Internal<WindowClass> internalWindowClass = new Internal<>(null);
    public final Internal<T> internalWindow = new Internal<>(null);

    public abstract void onInitialize(T disposable);

    public final String getName() {
        var hasAnnotation = this.getClass().isAnnotationPresent(ApplicationConfiguration.class);
        if (hasAnnotation) {
            return getClass().getAnnotation(ApplicationConfiguration.class)
                    .name();
        } else return "Windows Application";
    }
}
