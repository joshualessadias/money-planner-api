package com.joshuadias.moneyplannerapi;

import com.joshuadias.moneyplannerapi.dto.requests.appUser.AppUserRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.appUser.RoleRequestDTO;
import com.joshuadias.moneyplannerapi.services.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoneyPlannerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyPlannerApiApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(AppUserService appUserService) {
//        return args -> {
//            appUserService.createRole(new RoleRequestDTO("ROLE_USER"));
//            appUserService.createRole(new RoleRequestDTO("ROLE_ADMIN"));
//            appUserService.createRole(new RoleRequestDTO("ROLE_MANAGER"));
//
//            appUserService.createAppUser(new AppUserRequestDTO("joshua", "joshua@gmail.com", "123456"));
//            appUserService.createAppUser(new AppUserRequestDTO("natali", "natali@gmail.com", "123456"));
//
//            appUserService.addRoleToAppUser(1L, 1L);
//            appUserService.addRoleToAppUser(1L, 2L);
//            appUserService.addRoleToAppUser(1L, 3L);
//            appUserService.addRoleToAppUser(2L, 3L);
//            appUserService.addRoleToAppUser(2L, 2L);
//        };
//    }

}
