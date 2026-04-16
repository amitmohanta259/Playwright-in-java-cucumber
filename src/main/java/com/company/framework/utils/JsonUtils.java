package com.company.framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, String>> readJsonAsListOfMaps(String path) {
        try {
            return objectMapper.readValue(new File(path), new TypeReference<>() {});
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read JSON data from: " + path, exception);
        }
    }
}
