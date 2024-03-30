package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.LabDataUpload;
import io.bootify.health_hive.domain.LabRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LabDataUploadRepository extends JpaRepository<LabDataUpload, Long> {

    LabDataUpload findFirstByLabRequest(LabRequest labRequest);

    List<LabDataUpload> findByLabRequestID(Long labRequestID);
}
