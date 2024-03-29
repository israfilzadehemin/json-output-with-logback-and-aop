package com.tutorials.jsonoutputwithlogbackaop.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record LogDetails(
        String event,
        String className,
        String methodName,
        Object input,
        Object output) {

    public static LogDetails ofStart(String className, String methodName, Map<String, Object> details) {
        return new LogDetails("START", className, methodName, details, null);
    }


    public static LogDetails ofEnd(String className, String methodName, Object result) {
        HashMap<String, Object> responseDetails = new HashMap<>();
        return new LogDetails("END", className, methodName, responseDetails, result);
    }

    public static LogDetails ofError(Map<String, Object> details) {
        return new LogDetails("EXCEPTION", null, null, details, null);
    }
}
