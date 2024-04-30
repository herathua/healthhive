package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.Lab;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabRepository extends JpaRepository<Lab, String> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByLabRegIDIgnoreCase(String labRegID);

}
