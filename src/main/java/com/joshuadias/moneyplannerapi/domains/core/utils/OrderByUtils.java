package com.joshuadias.moneyplannerapi.domains.core.utils;

import com.joshuadias.moneyplannerapi.domains.core.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.domains.core.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;

public class OrderByUtils {
    public static Sort getSorting(String sort) {
        if (sort == null) {
            return Sort.unsorted();
        }
        var customSort = splitSort(sort);
        return Sort.by(customSort.getDirection(), customSort.getColumn());
    }

    public static CustomSort splitSort(String sort) {
        if (sort == null || !sort.matches(".+:(asc|desc)")) {
            return null;
        }

        var sortSplit = sort.split(":");
        var field = sortSplit[0];
        var direction = sortSplit[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        return new CustomSort(field, direction);
    }

    public static void validateOrderBy(String orderBy) {
        if (orderBy == null) return;
        var ORDER_BY_PATTERN = ".+:(asc|desc)$";
        if (!orderBy.matches(ORDER_BY_PATTERN)) {
            throw new BadRequestException(MessageEnum.ORDER_BY_VALIDATION_ERROR.getMessage());
        }
    }

    @AllArgsConstructor
    @Data
    public static class CustomSort {
        private String column;
        private Sort.Direction direction;
    }
}
