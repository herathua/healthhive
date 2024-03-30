package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.Lab;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabRepository extends JpaRepository<Lab, Long> {

    boolean existsByLabRegIDIgnoreCase(String labRegID);

    boolean existsByEmailIgnoreCase(String email);

}
