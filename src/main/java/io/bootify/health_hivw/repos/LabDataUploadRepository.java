package io.bootify.health_hivw.repos;

import io.bootify.health_hivw.domain.LabDataUpload;
import io.bootify.health_hivw.domain.LabRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LabDataUploadRepository extends JpaRepository<LabDataUpload, Long> {

    LabDataUpload findFirstByLabRequest(LabRequest labRequest);

    List<LabDataUpload> findByLabRequestID(Long labRequestID);
}
