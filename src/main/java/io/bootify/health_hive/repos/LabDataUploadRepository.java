package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.LabDataUpload;
import io.bootify.health_hive.domain.LabRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LabDataUploadRepository extends JpaRepository<LabDataUpload, Long> {

    LabDataUpload findFirstByLabRequest(LabRequest labRequest);

    @Query("SELECT l FROM LabDataUpload l WHERE l.labRequest.id = :labRequestId")
    List<LabDataUpload> findByLabRequestId(@Param("labRequestId") Long labRequestId);
}
