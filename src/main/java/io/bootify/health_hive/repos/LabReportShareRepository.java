package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LabReportShareRepository extends JpaRepository<LabReportShare, Long> {

    LabReportShare findFirstByPatient(User user);

    LabReportShare findFirstByDoctor(User user);

    List<LabReportShare> findAllByDoctor (User user);

    boolean existsByPatientId(Long id);

    boolean existsByDoctorId(Long id);


}
