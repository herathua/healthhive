package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.FileHash;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileHashRepository extends JpaRepository<FileHash, Long> {
}
