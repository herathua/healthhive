package io.bootify.health_hivw.repos;

import io.bootify.health_hivw.domain.Lab;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabRepository extends JpaRepository<Lab, Long> {
}
