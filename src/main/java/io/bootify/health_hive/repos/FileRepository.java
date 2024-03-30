package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.DataUploadReqeust;
import io.bootify.health_hive.domain.File;
import io.bootify.health_hive.domain.LabDataUpload;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Long> {

    File findFirstByLabDataUpload(LabDataUpload labDataUpload);

    File findFirstByDataUploadReqeust(DataUploadReqeust dataUploadReqeust);

}
