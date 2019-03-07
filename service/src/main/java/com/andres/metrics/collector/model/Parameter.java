package com.andres.metrics.collector.model;

public class Parameter {

    public static final String NAME_TAG = "name";
    public static final String VALUE_TAG = "value";

    private String name;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
