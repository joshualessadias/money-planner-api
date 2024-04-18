package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.models.AppUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends GenericRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
