package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.RestaurantModel;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RestaurantModelAssembler {

    private final ModelMapper modelMapper;

    public RestaurantModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestaurantModel toModel(Restaurant restaurant) {
        return this.modelMapper.map(restaurant, RestaurantModel.class);
    }

    public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> toModel(restaurant)).collect(toList());
    }

}
