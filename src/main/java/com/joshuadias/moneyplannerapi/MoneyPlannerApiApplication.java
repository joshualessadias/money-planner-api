package com.joshuadias.moneyplannerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoneyPlannerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyPlannerApiApplication.class, args);
    }

}
