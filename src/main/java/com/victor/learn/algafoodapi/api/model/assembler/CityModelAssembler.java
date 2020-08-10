package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.CityModel;
import com.victor.learn.algafoodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CityModelAssembler {

    private final ModelMapper modelMapper;

    public CityModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CityModel toModel(City city) {
        return this.modelMapper.map(city, CityModel.class);
    }

    public List<CityModel> toCollectionModel(List<City> cities) {
        return cities.stream().map(state -> toModel(state)).collect(toList());
    }

}
