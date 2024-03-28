package com.joshuadias.moneyplannerapi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;

public class OrderByUtils {
    public static Sort getSorting(String sort) {
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

    @AllArgsConstructor
    @Data
    public static class CustomSort {
        private String column;
        private Sort.Direction direction;
    }
}
