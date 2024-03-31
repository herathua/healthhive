package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.DataUploadRequest;
import io.bootify.health_hive.domain.File;
import io.bootify.health_hive.domain.LabDataUpload;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Long> {

    File findFirstByLabDataUpload(LabDataUpload labDataUpload);

    File findFirstByDataUploadRequest(DataUploadRequest dataUploadRequest);

}
