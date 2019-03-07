package com.andres.metrics.collector.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MetricCollectorLogger {

    private static final Logger logger = LoggerFactory.getLogger(MetricCollectorLogger.class);
    private static final Method[] methods = logger.getClass().getMethods();

    public static void logMessage(LoggingMessages logMessage, Object... parameters) {
        try {
            Arrays.stream(methods)
                    .filter(method -> method.getName().equalsIgnoreCase(logMessage.getLevel().name())
                            && method.getParameterCount() == 1
                            && method.getGenericParameterTypes()[0].getTypeName().equalsIgnoreCase(String.class.getName())
                    )
                    .findFirst()
                    .get()
                    .invoke(logger, logMessage.getLogMessage(parameters));
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void logMessage(Throwable throwable, LoggingMessages logMessage, Object... parameters) {
        logMessage(logMessage, getStringStackTrace(throwable), parameters);
    }

    private static String getStringStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
