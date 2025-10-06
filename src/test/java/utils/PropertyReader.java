package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReader {

    private static final String propertiesPath = "/config.properties";
    private static volatile Properties properties;

    private PropertyReader() {}

    private static Properties load() {
        if (properties == null) {
            synchronized (PropertyReader.class) {
                if (properties == null) {
                    Properties p = new Properties();
                    try (InputStream is = PropertyReader.class.getResourceAsStream(propertiesPath)) {
                        if (is != null) p.load(is);
                    } catch (IOException ignored) { }
                    properties = p;
                }
            }
        }
        return properties;
    }

    public static String getProperty(String name) {
        String sys = System.getProperty(name);
        if (sys != null && !sys.isBlank()) return sys;
        String env = System.getenv(name.toUpperCase());
        if (env != null && !env.isBlank()) return env;
        return load().getProperty(name);
    }

    public static String getProperty(String name, String defaultValue) {
        String val = getProperty(name);
        return val == null ? defaultValue : val;
    }
}
