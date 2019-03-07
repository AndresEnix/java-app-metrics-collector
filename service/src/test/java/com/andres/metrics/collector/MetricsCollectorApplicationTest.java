package com.andres.metrics.collector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MetricsCollectorApplicationTest extends DbSetupTest{

    private static final int PERIOD_IN_MILLIS = 15000;

    @Test
    void schedulerDBValidationTest() throws Exception {
        Assertions.assertTrue(existElementWithText(logsList, "METRIC_00001 - [METRIC] - {\"collector_metric\"" +
                ":\"five-seconds-period\",\"query_index\":0,\"events\":1,\"collector_service\":\"service-1\"}"));
    }

    @Override
    protected int getPeriodInMillis() {
        return PERIOD_IN_MILLIS;
    }
}