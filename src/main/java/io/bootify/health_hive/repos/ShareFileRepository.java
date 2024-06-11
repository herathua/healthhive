package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.ShareFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface ShareFileRepository extends JpaRepository<ShareFile, Long> {

    ShareFile findFirstByLabReportShare(LabReportShare labReportShare);


    @Query("SELECT sf FROM ShareFile sf WHERE sf.dateCreated < :dateTime")
    List<ShareFile> findFilesCreatedBefore(LocalDateTime dateTime);





}
