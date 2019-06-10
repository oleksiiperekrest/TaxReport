package org.taxreport.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
    private ObjectMapper objectMapper;
    private static JsonUtil instance = null;

    private JsonUtil() {
        objectMapper = new ObjectMapper();
    }

    public static JsonUtil getInstance() {
        if (instance==null) {
            instance = new JsonUtil();
        }
        return instance;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
