package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.utils.ModelMapperUtils;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractConverter {
    @Autowired
    private ModelMapperUtils modelMapperUtils;

    public <C, T> Page<C> convertToPageDTO(Page<T> page, Class<C> clazz) {
        return page.map(entity -> convertToSingleDTO(entity, clazz));
    }

    public <C, T> List<C> convertToListDTO(List<T> list, Class<C> clazz) {
        return modelMapperUtils.mapAll(list, clazz);
    }

    public <C, T> List<C> convertToListDTO(List<T> list, Class<C> clazz, PropertyMap<T, C> propertyMap) {
        return modelMapperUtils.mapAll(list, clazz, propertyMap);
    }

    public <C> C convertToSingleDTO(Object entity, Class<C> clazz) {
        return modelMapperUtils.map(entity, clazz);
    }

    public <P, C> C convertToSingleDTO(P entity, Class<C> clazz, PropertyMap<P, C> propertyMap) {
        return modelMapperUtils.map(entity, clazz, propertyMap);
    }
}
