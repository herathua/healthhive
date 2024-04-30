package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabReportShareRepository extends JpaRepository<LabReportShare, Long> {

    LabReportShare findFirstByPatient(User user);

    LabReportShare findFirstByDoctor(User user);

    boolean existsByPatientUserEmailIgnoreCase(String userEmail);

    boolean existsByDoctorUserEmailIgnoreCase(String userEmail);

}
