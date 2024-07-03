package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.Notes;
import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotesRepository extends JpaRepository<Notes, Long> {

    Notes findFirstByUser(User user);

}
