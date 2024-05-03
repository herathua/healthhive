package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.DataUploadRequest;
import io.bootify.health_hive.domain.File;
import io.bootify.health_hive.domain.LabDataUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT f FROM File f JOIN f.dataUploadRequest dur JOIN dur.user u WHERE u.id = :userId")
    List<File> findAllByUserId(Long userId);

    File findFirstByLabDataUpload(LabDataUpload labDataUpload);

    File findFirstByDataUploadRequest(DataUploadRequest dataUploadRequest);

    List<File> findByCreatedDateBefore(LocalDateTime fiveMinutesAgo);

    List<File> findFirst5ByDataUploadRequestIdOrderByLastUpdatedDesc(Long dataUploadRequestId, Pageable pageable);

}
