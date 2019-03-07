package com.andres.metrics.collector.model;

public enum DatabaseJobConfigTag {

    STATEMENT_PARAMETER("statement"),
    PARAMETERS_PARAMETER("parameters"),
    TIMEOUT_PARAMETER("query-timeout"),
    MAX_ROWS_PARAMETER("query-max-rows");

    private final String tag;

    DatabaseJobConfigTag(String tag) {
        this.tag = tag;
    }

    public String tag() {
        return tag;
    }
}