package com.joshuadias.moneyplannerapi.domains.gemini.repositories;

import com.joshuadias.moneyplannerapi.domains.gemini.models.ContextMessage;
import com.joshuadias.moneyplannerapi.domains.shared.base.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContextMessageRepository extends GenericRepository<ContextMessage, Long> {

    Optional<ContextMessage> findByOrderByIdDesc();
}
