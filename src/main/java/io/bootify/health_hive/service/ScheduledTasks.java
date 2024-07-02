package io.bootify.health_hive.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

import java.util.List;

@Component
public class ScheduledTasks {

    private final TempFileService tempFileService;
    private final ShareFileService shareFileService;

    public ScheduledTasks(final TempFileService tempFileService, final ShareFileService shareFileService) {
        this.tempFileService = tempFileService;
        this.shareFileService = shareFileService;
    }

    @Scheduled(fixedRate = 300000)
    public void performTask() {
        System.out.println("Performing scheduled task" + LocalDateTime.now());
        try {
            List<Long> fileIds = tempFileService.getFiles();
            for (Long fileId : fileIds) {
                System.out.println("Processing file with id: " + fileId);
                try {
                    shareFileService.delete(fileId);
                    System.out.println("Deleted file with id: " + fileId);
                } catch (Exception e) {
                    System.out.println("Error deleting file with id: " + fileId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
