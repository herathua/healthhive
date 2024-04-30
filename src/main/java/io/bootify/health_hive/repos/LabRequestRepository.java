package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.domain.User;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface LabRequestRepository extends JpaRepository<LabRequest, Long> {

    // Find all LabRequest records by the associated lab ID
    List<LabRequest> findAllByLabId(Long labId);
    LabRequest findFirstByUser(User user);

    LabRequest findFirstByLab(Lab lab);

    boolean existsByUserId(Long id);

    boolean existsByLabId(Long id);

    interface FileServiceImpl {

        String saveFile(MultipartFile file);

        byte[] loadFile(String hash);
    }
}
