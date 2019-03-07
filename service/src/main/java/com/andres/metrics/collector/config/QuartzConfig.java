package com.andres.metrics.collector.config;

import com.andres.metrics.collector.model.Collector;
import com.andres.metrics.collector.model.Metric;
import com.andres.metrics.collector.model.Service;
import com.andres.metrics.collector.factory.CollectorJobFactory;
import com.andres.metrics.collector.metrics.LoggingMessages;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class QuartzConfig {

    public static final String PROP_THREAD_COUNT_SUFFIX = ".threadCount";
    public static final String PROP_THREAD_COUNT = StdSchedulerFactory.PROP_THREAD_POOL_PREFIX + PROP_THREAD_COUNT_SUFFIX;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Collector collector;

    @Autowired
    private SpringBeanJobFactory springBeanJobFactory;

    @Autowired
    private StdSchedulerFactory schedulerFactory;

    @Value("${metric_collector_blacklist:}")
    private Set<String> blackList;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public StdSchedulerFactory schedulerFactory() {
        return new StdSchedulerFactory();
    }

    @Bean
    public Set<Scheduler> scheduler() throws SchedulerException {
        Set<Scheduler> schedulers = new HashSet<>();
        Scheduler scheduler;
        for (Service service : getServicesInWitheList()) {
            scheduler = getScheduler(service);
            addJobsToScheduler(scheduler, service);
            scheduler.start();
            schedulers.add(scheduler);
        }
        return schedulers;
    }

    private Scheduler getScheduler(Service service) throws SchedulerException {
        schedulerFactory.initialize(createSchedulerProperties(service));
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.setJobFactory(springBeanJobFactory);
        return scheduler;
    }

    private void addJobsToScheduler(Scheduler scheduler, Service service) throws SchedulerException {
        JobDetail jobDetail;
        for (Metric metric : getEnabledMetrics(service.getMetrics())) {
            jobDetail = CollectorJobFactory.getJobDetail(service.getName(), metric);
            scheduler.scheduleJob(jobDetail, trigger(jobDetail, metric));
        }
    }

    private List<Service> getServicesInWitheList() {
        return collector.getServices()
                .stream()
                .filter(service -> ! blackList.contains(service.getName()))
                .collect(Collectors.toList());
    }

    private List<Metric> getEnabledMetrics(List<Metric> metrics) {
        return metrics
                .stream()
                .filter(metric -> metric.getEnabled())
                .collect(Collectors.toList());
    }

    private Properties createSchedulerProperties(Service service) {
        Properties properties = new Properties();
        properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, service.getName());
        properties.put(PROP_THREAD_COUNT, service.getThreads());
        return properties;
    }

    private Trigger trigger(JobDetail jobDetail, Metric metric) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(metric.getName())
                .withDescription(LoggingMessages.MESSAGE_00002.getMessage(metric))
                .withSchedule(CronScheduleBuilder.cronSchedule(metric.getCronTab()))
                .build();
    }
}
