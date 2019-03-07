package com.andres.metrics.collector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("black_list_cases")
class DbBlackListTest extends DbSetupTest{

    private static final int PERIOD_IN_MILLIS = 5000;
    private static final String SERVICE_BLACK_LIST = "black_list_service";

    static {
        System.setProperty("metric_collector_blacklist", SERVICE_BLACK_LIST);
    }

    @Test
    void blackListWhenSomeElementIsIncludedValidationTest() throws Exception {

        Assertions.assertTrue(existElementWithText(logsList, "METRIC_00001 - [METRIC] - {\"collector_metric\"" +
                ":\"five-seconds-period\",\"query_index\":0,\"events\":9,\"collector_service\":\"white_list_service\"}"));
        Assertions.assertTrue(!existElementWithText(logsList, "black_list_service"));
    }

    @Override
    protected int getPeriodInMillis() {
        return PERIOD_IN_MILLIS;
    }
}