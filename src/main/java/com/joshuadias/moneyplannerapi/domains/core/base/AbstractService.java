package com.joshuadias.moneyplannerapi.domains.core.base;

import com.joshuadias.moneyplannerapi.domains.core.utils.OrderByUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class AbstractService extends AbstractConverter {
    @PersistenceContext
    public EntityManager entityManager;

    public Pageable generatePageable(Integer page, Integer size) {
        if (page == null || size == null)
            return null;
        if (page - 1 > -1) page = page-1;
        return PageRequest.of(page, size);
    }

    public Pageable generatePageable(Integer page, Integer size, Sort sort) {
        if (page == null || size == null)
            return null;
        if (page - 1 > -1) page = page-1;
        return PageRequest.of(page, size, sort);
    }

    public Sort getSorting(String orderBy) {
        return OrderByUtils.getSorting(orderBy);
    }

    public OrderByUtils.CustomSort getCustomSort(String orderBy) {
        return OrderByUtils.splitSort(orderBy);
    }
}
