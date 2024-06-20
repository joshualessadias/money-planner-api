package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.base.GenericRepository;
import com.joshuadias.moneyplannerapi.models.AppRole;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends GenericRepository<AppRole, Long> {
}
