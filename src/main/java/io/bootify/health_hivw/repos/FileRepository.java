package io.bootify.health_hivw.repos;

import io.bootify.health_hivw.domain.DataUploadReqeust;
import io.bootify.health_hivw.domain.File;
import io.bootify.health_hivw.domain.LabDataUpload;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Long> {

    File findFirstByLabDataUpload(LabDataUpload labDataUpload);

    File findFirstByDataUploadReqeust(DataUploadReqeust dataUploadReqeust);

}
