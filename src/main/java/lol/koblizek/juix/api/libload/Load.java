package lol.koblizek.juix.api.libload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specifies which native libraries include as user-defined
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Load {
    String[] value();
}
