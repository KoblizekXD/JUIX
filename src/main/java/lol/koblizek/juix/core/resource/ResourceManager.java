package lol.koblizek.juix.core.resource;

public final class ResourceManager {
    private ResourceManager() {}

    public static ResourceManager getInstance() {
        return new ResourceManager();
    }
    public Resource getResource(String location) {
        return new Resource(getClass().getResource(location), getClass().getResourceAsStream(location));
    }
}
