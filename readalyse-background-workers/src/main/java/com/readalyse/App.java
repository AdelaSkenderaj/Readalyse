package com.readalyse;

import com.readalyse.job.AnalyseAllFiles;
import com.readalyse.util.FileRetrieval;
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

        // Define Job 1
        JobDetail allTimeFileRetrieval = JobBuilder.newJob(AnalyseAllFiles.class)
                .withIdentity("allTimeFileAnalysis", "group1")
                .build();

        /*// Define Job 2
        JobDetail jobDetail2 = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob2", "group1")
                .usingJobData(jobDataMap2)
                .build();*/

        // Define triggers for both jobs (e.g., run every 10 seconds)
        Trigger triggerAllTimeFileRetrieval = TriggerBuilder.newTrigger()
                .withIdentity("triggerAllTimeFileAnalysis", "group1")
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
