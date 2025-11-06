package ru.itmo.infsec.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ru.itmo.infsec")
@EntityScan(basePackages = "ru.itmo.infsec.entity")
@EnableJpaRepositories(basePackages = "ru.itmo.infsec.repository")
public class InfSecExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfSecExampleApplication.class, args);
    }
}
