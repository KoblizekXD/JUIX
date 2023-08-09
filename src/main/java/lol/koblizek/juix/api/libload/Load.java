package lol.koblizek.juix.api.libload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Load {
    String[] value();
}
