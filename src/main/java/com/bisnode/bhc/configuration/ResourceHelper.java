package com.bisnode.bhc.configuration;

/**
 * Created by sahm on 25.08.17.
 */

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Resource-related helper methods
 */
public final class ResourceHelper {

    /**
     * Reads a file from the classpath
     * @param path the absolute path
     * @return a text reader that contains the entire file content in UTF-8
     */
    public static StringReader getResourceReader(String path) {
        return getResourceReader(path, StandardCharsets.UTF_8);
    }

    /**
     * Reads a file from the classpath
     * @param path the absolute path
     * @param charset the character set to use
     * @return a text reader that contains the entire file content in UTF-8
     */
    public static StringReader getResourceReader(String path, Charset charset) {
        return new StringReader(getResourceAsString(path, charset));
    }

    /**
     * Reads a file from the classpath
     * @param path the absolute path
     * @return a text reader that contains the entire file content in UTF-8
     */
    public static String getResourceAsString(String path) {
        return getResourceAsString(path, StandardCharsets.UTF_8);
    }

    /**
     * Reads a file from the classpath
     * @param path the absolute path
     * @param charset the character set to use
     * @return a text reader that contains the entire file content in UTF-8
     */
    public static String getResourceAsString(String path, Charset charset) {
        try (InputStream stream = getResource(path).openStream()) {
            return CharStreams.toString(new InputStreamReader(stream, charset));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid resource: " + path, e);
        }
    }

    /**
     * @param path the absolute path
     * @return the resource URL, never <code>null</code>
     */
    public static URL getResource(String path) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return url;
    }

    private ResourceHelper() {
        // no instances
    }
}
