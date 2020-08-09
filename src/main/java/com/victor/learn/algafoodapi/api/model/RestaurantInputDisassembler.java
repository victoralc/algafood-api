package com.victor.learn.algafoodapi.api.model;

import com.victor.learn.algafoodapi.api.model.input.RestaurantInput;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    public Restaurant toDomainObject(RestaurantInput restaurantInput) {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(restaurantInput.getCuisine().getId());

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantInput.getName());
        restaurant.setDeliveryTax(restaurantInput.getDeliveryTax());
        restaurant.setCuisine(cuisine);

        return restaurant;
    }
    
}
