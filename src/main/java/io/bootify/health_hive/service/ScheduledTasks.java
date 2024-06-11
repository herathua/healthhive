package io.bootify.health_hive.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import io.bootify.health_hive.service.ShareFileService;

import java.util.List;

@Component
public class ScheduledTasks {

    private final TempFileService tempFileService;
    private final ShareFileService shareFileService;

    public ScheduledTasks(final TempFileService tempFileService, final ShareFileService shareFileService) {
        this.tempFileService = tempFileService;
        this.shareFileService = shareFileService;
    }

    @Scheduled(fixedRate = 10000)
    public void performTask() {
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
