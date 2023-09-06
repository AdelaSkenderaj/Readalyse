package com.readalyse;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // Start the scheduler
        scheduler.start();

        // Define parameters for Job 1
        JobDataMap allTimeData = new JobDataMap();
        allTimeData.put("basePath", "C:/Users/Dela/background-workers/all-time-data");
        allTimeData.put("rdfFilesPath", "C:/Users/Dela/background-workers/all-time-data/rdf-files.tar.bz2");
        allTimeData.put("rdfUrl", "https://gutenberg.org/cache/epub/feeds/rdf-files.tar.bz2");

        // Define parameters for Job 2
        JobDataMap jobDataMap2 = new JobDataMap();
        jobDataMap2.put("basePath", "C:/Users/Dela/background-workers/daily-data");
        jobDataMap2.put("parameter2", 456);

        // Define Job 1
        JobDetail allTimeFileRetrieval = JobBuilder.newJob(FileRetrieval.class)
                .withIdentity("allTimeFileRetrieval", "group1")
                .usingJobData(allTimeData)
                .build();

        /*// Define Job 2
        JobDetail jobDetail2 = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob2", "group1")
                .usingJobData(jobDataMap2)
                .build();*/

        // Define triggers for both jobs (e.g., run every 10 seconds)
        Trigger triggerAllTimeFileRetrieval = TriggerBuilder.newTrigger()
                .withIdentity("triggerAllTimeFileRetrieval", "group1")
                .startNow()
                .build();

        /*Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();*/

        // Schedule both jobs with their respective triggers
        scheduler.scheduleJob(allTimeFileRetrieval, triggerAllTimeFileRetrieval);
//        scheduler.scheduleJob(jobDetail2, trigger2);
    }
}
