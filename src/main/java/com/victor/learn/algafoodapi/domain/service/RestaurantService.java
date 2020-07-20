package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.RestaurantNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineService cuisineService;

    public Restaurant findOrFail(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }

    public Restaurant save(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Cuisine found = cuisineService.findOrFail(cuisineId);
        restaurant.setCuisine(found);
        return restaurantRepository.save(restaurant);
    }

}
