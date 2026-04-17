package com.company.framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties = new Properties();

    public ConfigReader() {
        try (FileInputStream stream = new FileInputStream(FrameworkConstants.CONFIG_PATH)) {
            properties.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load config.properties", exception);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public boolean getBoolean(String key) {
        String overrideValue = System.getProperty(key);
        if (overrideValue != null) {
            return Boolean.parseBoolean(overrideValue.trim());
        }
        return Boolean.parseBoolean(get(key));
    }
}
