package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.DataUploadRequest;
import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DataUploadRequestRepository extends JpaRepository<DataUploadRequest, Long> {

    DataUploadRequest findFirstByUser(User user);

}
