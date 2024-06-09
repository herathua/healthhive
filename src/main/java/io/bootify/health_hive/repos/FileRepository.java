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


    File findFirstByLabDataUpload(LabDataUpload labDataUpload);

    File findFirstByDataUploadRequest(DataUploadRequest dataUploadRequest);

    List<File> findByCreatedDateBefore(LocalDateTime fiveMinutesAgo);

    List<File> findTop5ByDataUploadRequestIdOrLabDataUploadIdOrderByLastUpdatedDesc(Integer dataUploadRequestId, Integer labDataUploadId);

//    @Query
//    List<File> findByDataUploadRequest(Long dataUploadRequestId);

}
