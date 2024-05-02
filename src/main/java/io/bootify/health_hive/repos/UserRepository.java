package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByNicIgnoreCase(String nic);

    User findByEmail(String email);

    User findByGender (String gender);
}
