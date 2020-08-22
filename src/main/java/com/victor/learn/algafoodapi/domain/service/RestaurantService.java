package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.RestaurantNotFoundException;
import com.victor.learn.algafoodapi.domain.model.City;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.repository.RestaurantRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CuisineService cuisineService;
    private final CityService cityService;

    public RestaurantService(RestaurantRepository restaurantRepository, CuisineService cuisineService, CityService cityService) {
        this.restaurantRepository = restaurantRepository;
        this.cuisineService = cuisineService;
        this.cityService = cityService;
    }

    public Restaurant findOrFail(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        val cuisineId = restaurant.getCuisine().getId();
        val cityId = restaurant.getAddress().getCity().getId();

        val found = cuisineService.findById(cuisineId);
        val city = cityService.findById(cityId);

        restaurant.setCuisine(found);
        restaurant.getAddress().setCity(city);

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void activate(Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.activate();
    }

    @Transactional
    public void inactivate(Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.inactivate();
    }

}
