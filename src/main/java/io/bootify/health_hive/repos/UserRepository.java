package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUserEmailIgnoreCase(String userEmail);

    boolean existsByNicIgnoreCase(String nic);

}
