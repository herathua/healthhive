package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.domain.LabRequest;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LabRepository extends JpaRepository<Lab, Long> {

    boolean existsByLabRegIDIgnoreCase(String labRegID);

    boolean existsByEmailIgnoreCase(String email);

   // MetricsDSLContext findByemail(String email);
   Optional<Lab> findAllByEmail(String email);
}
