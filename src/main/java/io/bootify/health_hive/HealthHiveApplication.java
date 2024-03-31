package io.bootify.health_hive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("io.bootify.health_hive.domain")
public class HealthHiveApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HealthHiveApplication.class, args);
    }

}
