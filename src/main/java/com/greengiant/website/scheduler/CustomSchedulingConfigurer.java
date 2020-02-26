package com.greengiant.website.scheduler;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

//@Lazy(false)
//@Configuration
//@EnableScheduling
public class CustomSchedulingConfigurer implements SchedulingConfigurer {

    private static String cron = "0 0/15 * * * ?";

    public static void setIntervalMinute(int minute) {
        cron = "0 0/" + minute + " * * * ?";
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
            new Runnable() {
                @Override
                public void run() {
                    // todo 具体定时任务的代码写在这里
                }
            },
            new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    // 任务触发，可修改任务的执行周期
                    CronTrigger trigger = new CronTrigger(cron);
                    Date nextExec = trigger.nextExecutionTime(triggerContext);
                    return nextExec;
                }
            }
        );
    }
}
