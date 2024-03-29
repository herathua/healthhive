package io.bootify.health_hivw.repos;

import io.bootify.health_hivw.domain.LabRequest;
import io.bootify.health_hivw.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabRequestRepository extends JpaRepository<LabRequest, Long> {

    LabRequest findFirstByUser(User user);

    boolean existsByUserId(Long id);

    boolean existsByLabId(Long id);

}
