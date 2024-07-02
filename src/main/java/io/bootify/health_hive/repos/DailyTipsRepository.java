package io.bootify.health_hive.repos;

import io.bootify.health_hive.domain.DailyTips;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DailyTipsRepository extends JpaRepository<DailyTips, Long> {
}
