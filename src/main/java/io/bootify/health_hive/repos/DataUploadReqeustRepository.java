package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.DataUploadReqeust;
import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DataUploadReqeustRepository extends JpaRepository<DataUploadReqeust, Long> {

    DataUploadReqeust findFirstByUser(User user);

}
