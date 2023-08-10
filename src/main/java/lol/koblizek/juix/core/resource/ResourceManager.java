package lol.koblizek.juix.core.resource;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Main resource manager used for managing resources, you can retrieve its instance
 * using {@link #getInstance()} method, this will return freshly baked ResourceManager.
 * Main feature of ResourceManager is {@link #getResource(String)} method, it's used to fetch a {@link Resource}
 * object, which you can further turn into stream, file or URL.
 * <p>
 * ResourceManager be also used to manager Properties in the juix.properties file(if found).
 *
 * @see #getInstance()
 * @see #getResource(String)
 * @see Resource
 * @author KoblizekXD
 */
@Log4j2
public final class ResourceManager {
    private ResourceManager() {}

    /**
     * @return freshly baked instance of {@link ResourceManager}
     */
    public static ResourceManager getInstance() {
        return new ResourceManager();
    }

    /**
     * Tries to retrieve a resource from resource directory, if filename equals to name.txt, location
     * must be /name.txt.
     *
     * @param location location of a resource, must start with '/' character
     * @return new instance of {@link Resource} class, or null if resource was not found
     */
    public Resource getResource(String location) {
        URL resource = getClass().getResource(location);
        if (resource == null) return null;
        return new Resource(resource, getClass().getResourceAsStream(location));
    }

    /**
     * @return Properties object
     */
    public Properties getProperties() {
        var props = getResource("/juix.properties");
        Properties properties = new Properties();
        try {
            properties.load(props.stream());
        } catch (IOException | NullPointerException e) {
            log.error("Failed to retrieve properties file: ", e);
        }
        return properties;
    }

    /**
     * @return true if juix.properties file exist, false otherwise
     */
    public boolean propertiesExist() {
        var props = getResource("/juix.properties");
        return props != null;
    }
}
