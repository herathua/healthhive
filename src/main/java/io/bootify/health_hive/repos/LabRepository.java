package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.domain.LabRequest;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LabRepository extends JpaRepository<Lab, Long> {

    boolean existsByLabRegIDIgnoreCase(String labRegID);

    boolean existsByEmailIgnoreCase(String email);

   // MetricsDSLContext findByemail(String email);
   Lab findAllByEmail(String email);


}
