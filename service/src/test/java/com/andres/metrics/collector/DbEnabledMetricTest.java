package com.andres.metrics.collector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("db_enabled_metric_cases")
class DbEnabledMetricTest extends DbSetupTest {

    private static final int PERIOD_IN_MILLIS = 5000;

    @Test
    void queryEnabledValidationTest() throws Exception {
        Assertions.assertTrue(existElementWithText(logsList, "METRIC_00001 - [METRIC] - {\"collector_metric\":\"enabled-metric\"," +
                "\"query_index\":0,\"events\":6,\"collector_service\":\"service-2\"}"));
        Assertions.assertTrue(!existElementWithText(logsList, "disabled-metric"));
    }

    @Override
    protected int getPeriodInMillis() {
        return PERIOD_IN_MILLIS;
    }
}