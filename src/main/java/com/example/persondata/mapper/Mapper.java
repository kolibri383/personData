package com.example.persondata.mapper;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper extends ModelMapper {

    public <T, D> List<T> mapCollections(List<D> sources, Class<T> destination) {
        return sources.stream().map(
                        (source) -> map(source, destination))
                .collect(Collectors.toList());
    }
}
