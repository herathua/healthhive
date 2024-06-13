package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HealthDataRepository extends JpaRepository<HealthData, Long> {
}
