package com.joshuadias.moneyplannerapi.domains.core.base;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class AbstractServiceRepository<R extends GenericRepository<T, I>, T, I extends Serializable>
        extends AbstractService {
    @Autowired(required = false)
    protected R repository;

    public T save(T entity) {
        return repository.save(entity);
    }
}
