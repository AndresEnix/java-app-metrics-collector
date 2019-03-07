package com.andres.metrics.collector.metrics;

import org.apache.logging.log4j.Level;

import java.text.MessageFormat;

public enum LoggingMessages {

    METRIC_00001(Level.INFO, "[METRIC] - {0}"),

    ERROR_00001(Level.ERROR, "{0}"),

    MESSAGE_00001(Level.INFO, "ServiceName: {2}, MetricName: {1}, JobDetail: {0}"),
    MESSAGE_00002(Level.INFO, "Trigger for the {0} metric");

    private Level level;
    private String message;
    private Object[] parameters;

    LoggingMessages(Level level, String message) {
        this.level = level;
        this.message = message;
    }


    public Level getLevel() {
        return level;
    }

    public String getMessage(Object... parameters) {
        this.parameters = parameters;
        return toString();
    }

    public String getLogMessage(Object... parameters) {
        this.parameters = parameters;
        this.level = this.level != null ? this.level : Level.OFF;
        return new StringBuilder(this.name())
                .append(" - ")
                .append(toString())
                .toString();
    }

    @Override
    public String toString() {
        return MessageFormat.format(message, parameters);
    }
}