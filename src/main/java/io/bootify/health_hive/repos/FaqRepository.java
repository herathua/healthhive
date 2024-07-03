package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FaqRepository extends JpaRepository<Faq, Long> {
}
