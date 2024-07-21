package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.HealthData;
import io.bootify.health_hive.domain.Notes;
import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotesRepository extends JpaRepository<Notes, Long> {

    List<Notes> findByUser_Id(Long Userid);

}
