package com.jobaggregator.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public final class ObjectMapperProvider {

    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .findAndAddModules()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private ObjectMapperProvider() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
