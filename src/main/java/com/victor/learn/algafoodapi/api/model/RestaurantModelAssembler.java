package com.victor.learn.algafoodapi.api.model;

import com.victor.learn.algafoodapi.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantModelAssembler {

    public RestaurantModel toModel(Restaurant restaurant) {
        CuisineModel cuisineModel = new CuisineModel();
        cuisineModel.setId(restaurant.getCuisine().getId());
        cuisineModel.setName(restaurant.getCuisine().getName());

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurant.getId());
        restaurantModel.setName(restaurant.getName());
        restaurantModel.setDeliveryTax(restaurant.getDeliveryTax());
        restaurantModel.setCuisine(cuisineModel);
        return restaurantModel;
    }
    
}
