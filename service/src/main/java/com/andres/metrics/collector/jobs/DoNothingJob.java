package com.andres.metrics.collector.jobs;

import com.andres.metrics.collector.metrics.LoggingMessages;
import com.andres.metrics.collector.metrics.MetricCollectorLogger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

public class DoNothingJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            MetricCollectorLogger.logMessage(LoggingMessages.MESSAGE_00001,
                    this.getClass().getSimpleName(),
                    jobExecutionContext.getJobDetail().getKey().getName(),
                    jobExecutionContext.getScheduler().getSchedulerName());
        } catch (SchedulerException e) {
            MetricCollectorLogger.logMessage(e, LoggingMessages.ERROR_00001);
        }
    }
}
