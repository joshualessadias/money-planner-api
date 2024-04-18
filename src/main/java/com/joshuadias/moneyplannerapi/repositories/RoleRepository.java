package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.models.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends GenericRepository<Role, Long> {
    Role findByName(String name);
}
