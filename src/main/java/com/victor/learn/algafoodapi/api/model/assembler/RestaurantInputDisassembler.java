package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.restaurant.RestaurantInput;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    private final ModelMapper modelMapper;

    public RestaurantInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurant toDomainObject(RestaurantInput restaurantInput) {
        return this.modelMapper.map(restaurantInput, Restaurant.class);
    }
    
    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
        restaurant.setCuisine(new Cuisine());
        modelMapper.map(restaurantInput, restaurant);
    }

}
