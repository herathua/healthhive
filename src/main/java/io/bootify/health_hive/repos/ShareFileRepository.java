package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.ShareFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ShareFileRepository extends JpaRepository<ShareFile, Long> {

    ShareFile findFirstByLabReportShare(LabReportShare labReportShare);

    @Query("SELECT s FROM ShareFile s WHERE s.doctorId = ?1")
    List<ShareFile> findByDoctorId(Long doctorId);

}
