package org.eu.nl.dndmapp.dmaserver.models;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RequestBodyExtractor {

    public static String getText(ObjectNode data, String fieldName) {
        if (data.get(fieldName) == null || !data.get(fieldName).isTextual()) return null;

        return data.get(fieldName).asText();
    }

    public static Integer getInteger(ObjectNode data, String fieldName) {
        if (data.get(fieldName) == null || !data.get(fieldName).isIntegralNumber()) return null;

        return data.get(fieldName).asInt();
    }

    public static Double getDouble(ObjectNode data, String fieldName) {
        if (data.get(fieldName) == null || !data.get(fieldName).isDouble()) return null;

        return data.get(fieldName).asDouble();
    }

    public static Boolean getBoolean(ObjectNode data, String fieldName) {
        if (data.get(fieldName) == null || !data.get(fieldName).isBoolean()) return null;

        return data.get(fieldName).asBoolean();
    }

    public static ArrayNode getList(ObjectNode data, String fieldName) {
        if (data.get(fieldName) == null || !data.get(fieldName).isArray()) return null;

        return data.withArray(fieldName);
    }
}
