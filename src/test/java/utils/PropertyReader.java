package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Утиль для чтения config.properties.
 * Ленивое чтение и возможность подгрузить дополнительный файл.
 */
public final class PropertyReader {

    private static String propertiesPath = "/config.properties";
    private static volatile Properties props;
    private static InputStream stream;

    private PropertyReader() {
    }

    private static String normalizePath() {
        if (propertiesPath.charAt(0) != '/') propertiesPath = "/" + propertiesPath;
        return propertiesPath;
    }

    public static Properties readAll() {
        props = new Properties();
        try {
            stream = PropertyReader.class.getResourceAsStream(normalizePath());
            if (stream != null) props.load(stream);
        } catch (Exception ex) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (props.getProperty("config_file") != null) {
            Properties extra = getProperties(props.getProperty("config_file"));
            props.putAll(extra);
        }
        return props;
    }

    private static Properties ensureLoaded() {
        return props != null ? props : readAll();
    }

    public static Properties getProperties(String path) {
        propertiesPath = path;
        return readAll();
    }

    public static String getProperty(String key) {
        return ensureLoaded().getProperty(key);
    }
}
