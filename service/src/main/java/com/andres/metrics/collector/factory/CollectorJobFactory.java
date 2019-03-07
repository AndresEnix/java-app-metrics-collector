package com.andres.metrics.collector.factory;

import com.andres.metrics.collector.jobs.DbQueryJob;
import com.andres.metrics.collector.jobs.DoNothingJob;
import com.andres.metrics.collector.metrics.LoggingMessages;
import com.andres.metrics.collector.model.DatabaseJobConfigTag;
import com.andres.metrics.collector.model.Metric;
import com.andres.metrics.collector.util.JSONConverter;
import org.quartz.JobDetail;

public class CollectorJobFactory {

    public static JobDetail getJobDetail(String serviceName, Metric metric) {
        switch (metric.getType()) {
            case DATABASE:
                return getDatabaseJob(serviceName, metric);
            default:
                return getDoNothingJob(serviceName, metric);
        }
    }

    private static JobDetail getDatabaseJob(String serviceName, Metric metric) {
        return org.quartz.JobBuilder.newJob().ofType(DbQueryJob.class)
                .storeDurably()
                .withIdentity(metric.getName())
                .withDescription(LoggingMessages.MESSAGE_00001.getMessage(DbQueryJob.class.getSimpleName(), metric.getName(), serviceName))
                .usingJobData(DatabaseJobConfigTag.STATEMENT_PARAMETER.tag(), metric.getStatement())
                .usingJobData(DatabaseJobConfigTag.TIMEOUT_PARAMETER.tag(), metric.getQueryTimeOut())
                .usingJobData(DatabaseJobConfigTag.MAX_ROWS_PARAMETER.tag(), metric.getQueryMaxRows())
                .usingJobData(DatabaseJobConfigTag.PARAMETERS_PARAMETER.tag(), JSONConverter.convertToJsonArray(metric.getParameters()).toJSONString())
                .build();
    }

    private static JobDetail getDoNothingJob(String serviceName, Metric metric) {
        return org.quartz.JobBuilder.newJob().ofType(DoNothingJob.class)
                .storeDurably()
                .withIdentity(metric.getName())
                .withDescription(LoggingMessages.MESSAGE_00001.getMessage(DoNothingJob.class.getSimpleName(), metric.getName(), serviceName))
                .build();
    }
}
