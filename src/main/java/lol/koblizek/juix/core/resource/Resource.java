package lol.koblizek.juix.core.resource;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Properties;

@Log4j2
public final class Resource {
    private final URL resourceURL;
    private final InputStream istream;

    public Resource(URL resourceURL, InputStream istream) {
        this.resourceURL = resourceURL;
        this.istream = istream;
    }
    public byte[] bytes() {
        try {
            return istream.readAllBytes();
        } catch (IOException e) {
            log.error("Failed to read bytes from resource stream");
            throw new RuntimeException(e);
        }
    }
    public File file() {
        try {
            return new File(resourceURL.toURI());
        } catch (URISyntaxException e) {
            log.error("URL syntax error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public InputStream stream() {
        return istream;
    }
}
