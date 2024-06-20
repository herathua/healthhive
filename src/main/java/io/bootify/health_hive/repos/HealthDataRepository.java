package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HealthDataRepository extends JpaRepository<HealthData, Long> {
    List<HealthData> findByUser_Id(Long Userid);
}
