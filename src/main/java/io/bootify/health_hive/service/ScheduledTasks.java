package io.bootify.health_hive.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 10000)
    public void performTask() {
        System.out.println("Scheduled task executed at " );
        // Your logic here
    }
}
