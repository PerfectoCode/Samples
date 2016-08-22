package com.perfecto.reporting.sample;

/**
 * Class for retrieving the value of required system properties.
 */
public class SystemPropertyGetter {

    /**
     * Throws an exception if the value is missing or null.
     * Used for validating existence of required parameters.
     *
     * @param key System property key
     * @return System property value
     */
    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing required system properties " + key);
        }
        return value;
    }
}
