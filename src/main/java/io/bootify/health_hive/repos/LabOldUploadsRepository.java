package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.LabOldUploads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;


public interface LabOldUploadsRepository extends JpaRepository<LabOldUploads, Long> {
    @Query("SELECT l FROM LabOldUploads l WHERE l.labid = :labid AND l.dateCreated BETWEEN :startDate AND :endDate")
    List<LabOldUploads> findByLabidAndDateCreatedBetween(
            @Param("labid") Long labid,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate
    );
}
