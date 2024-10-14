package com.joshuadias.moneyplannerapi.domains.core.repositories;

import com.joshuadias.moneyplannerapi.domains.shared.base.GenericRepository;
import com.joshuadias.moneyplannerapi.domains.core.models.AppRole;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends GenericRepository<AppRole, Long> {
}
