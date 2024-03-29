package io.bootify.health_hivw.repos;

import io.bootify.health_hivw.domain.DataUploadReqeust;
import io.bootify.health_hivw.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DataUploadReqeustRepository extends JpaRepository<DataUploadReqeust, Long> {

    DataUploadReqeust findFirstByUser(User user);

}
