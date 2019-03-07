package com.andres.metrics.collector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("db_query_cases")
class DbJobTest extends DbSetupTest {

    private static final int PERIOD_IN_MILLIS = 5000;

    @Test
    void queryConstructionValidationTest() throws Exception {
        Assertions.assertTrue(existElementWithText(logsList, "ERROR_00001 - org.h2.jdbc.JdbcSQLSyntaxErrorException: Table \"DUMMY_TABLE_1\" not found;"));
        Assertions.assertTrue(existElementWithText(logsList, "METRIC_00001 - [METRIC] - {\"receivedevents\":6,\"collector_metric\":\"well-constructed-query\",\"query_index\":0,\"collector_" +
                "service\":\"service-3\"}"));
    }

    @Override
    protected int getPeriodInMillis() {
        return PERIOD_IN_MILLIS;
    }
}