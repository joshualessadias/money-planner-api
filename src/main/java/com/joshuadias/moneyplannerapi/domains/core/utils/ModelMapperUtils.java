package com.joshuadias.moneyplannerapi.domains.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ConfigurationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ModelMapperUtils {
    private final List<String> propertyMapList;
    private ModelMapper modelMapperStrict;
    private ModelMapper modelMapperDefault;

    public ModelMapperUtils() {
        propertyMapList = new ArrayList<>();
        loadModelMapperStrict();
        loadModelMapperDefault();
    }

    private void loadModelMapperStrict() {
        modelMapperStrict = new ModelMapper();
        modelMapperStrict.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private void loadModelMapperDefault() {
        modelMapperDefault = new ModelMapper();
    }

    private <T, D> String generateKeyPropertyMap(Class<T> origin, Class<D> target) {
        StringJoiner propertyMap = new StringJoiner("::");
        propertyMap.add(origin.getName());
        propertyMap.add(target.getName());
        return propertyMap.toString();
    }

    public <D, T> D map(final T origin, Class<D> target, PropertyMap<T, D> propertyMaps) {
        return modelMapperHandler(propertyMaps, origin, target);
    }

    public <D, T> D map(final T origin, Class<D> target) {
        return modelMapperHandler(null, origin, target);
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass, PropertyMap<T, D> propertyMaps) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass, propertyMaps))
                .collect(Collectors.toList());
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public <D, T> D modelMapperHandler(PropertyMap<T, D> propertyMaps, T origin, Class<D> target) {
        String keyPropertyMap;
        if (propertyMaps != null) {
            keyPropertyMap = generateKeyPropertyMap(origin.getClass(), target);
            if (!propertyMapList.contains(keyPropertyMap)) {
                List.of(propertyMaps).forEach(propertyMap -> {
                    try {
                        modelMapperStrict.addMappings(propertyMap);
                    } catch (ConfigurationException e) {
                        if (!e.getMessage().contains("mapping already exists")) {
                            log.error(String.valueOf(e));
                        }
                    }
                });
                propertyMapList.add(keyPropertyMap);
            }
            return modelMapperStrict.map(origin, target);
        }
        return modelMapperDefault.map(origin, target);
    }
}
