package lol.koblizek.juix.core.resource;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@Log4j2
public final class ResourceManager {
    private ResourceManager() {}

    public static ResourceManager getInstance() {
        return new ResourceManager();
    }
    public Resource getResource(String location) {
        URL resource = getClass().getResource(location);
        if (resource == null) return null;
        return new Resource(resource, getClass().getResourceAsStream(location));
    }
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
    public boolean propertiesExist() {
        var props = getResource("/juix.properties");
        return props != null;
    }
}
