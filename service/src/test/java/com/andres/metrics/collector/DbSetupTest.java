package com.andres.metrics.collector;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.andres.metrics.collector.metrics.MetricCollectorLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DbSetupTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MetricCollectorLogger.class);
    private static ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    protected static List<ILoggingEvent> logsList;
    private static final int PERIOD_IN_MILLIS = 5000;

    static {
        System.setProperty("metric_collector_profile", "default");
        System.setProperty("db_max_pool_size", "2");
    }

    @BeforeEach
    void start() {

        Logger collectorLogger = (Logger) LoggerFactory.getLogger(MetricCollectorLogger.class);
        listAppender.start();
        collectorLogger.addAppender(listAppender);
        logsList = listAppender.list;
        try {
            Thread.sleep(getPeriodInMillis());
        } catch (InterruptedException e) {
            logger.error("Period was not accomplished.", e.getMessage());
        }
    }

    @AfterEach
    void shutdown() {
        listAppender.clearAllFilters();
    }

    protected int getPeriodInMillis(){
        return PERIOD_IN_MILLIS;
    }

    protected boolean existElementWithText(List<ILoggingEvent> logsList, String text){
        boolean found = false;
        for(ILoggingEvent element : logsList){
            if(element.getMessage().contains(text)){
                found = true;
            }
        }

        return found;
    }
}