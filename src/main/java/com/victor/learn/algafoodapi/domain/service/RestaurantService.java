package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.RestaurantNotFoundException;
import com.victor.learn.algafoodapi.domain.model.PaymentType;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.model.User;
import com.victor.learn.algafoodapi.domain.repository.RestaurantRepository;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CuisineService cuisineService;
    private final CityService cityService;
    private final PaymentTypeService paymentTypeService;
    private final UserService userService;

    public RestaurantService(RestaurantRepository restaurantRepository, CuisineService cuisineService, CityService cityService, PaymentTypeService paymentTypeService, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.cuisineService = cuisineService;
        this.cityService = cityService;
        this.paymentTypeService = paymentTypeService;
        this.userService = userService;
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

    @Transactional
    public void activate(List<Long> restaurantIds) {
        restaurantIds.forEach(this::activate);
    }

    @Transactional
    public void inactivate(List<Long> restaurantIds) {
        restaurantIds.forEach(this::inactivate);
    }

    @Transactional
    public void disassociatePaymentTypes(Long restaurantId, Long paymentTypeId) {
        Restaurant restaurant = findOrFail(restaurantId);
        final PaymentType paymentType = paymentTypeService.findById(paymentTypeId);
        restaurant.removePaymentType(paymentType);
    }

    @Transactional
    public void associatePaymentTypes(Long restaurantId, Long paymentTypeId) {
        Restaurant restaurant = findOrFail(restaurantId);
        final PaymentType paymentType = paymentTypeService.findById(paymentTypeId);
        restaurant.addPaymentType(paymentType);
    }

    @Transactional
    public void open(Long restaurantId) {
        final Restaurant restaurant = findOrFail(restaurantId);
        restaurant.open();
    }

    @Transactional
    public void close(Long restaurantId) {
        final Restaurant restaurant = findOrFail(restaurantId);
        restaurant.close();
    }

    @Transactional
    public void removeResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = findOrFail(restaurantId);
        User user = userService.findOrFail(userId);

        restaurant.removeResponsible(user);
    }

    @Transactional
    public void addResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = findOrFail(restaurantId);
        User user = userService.findOrFail(userId);

        restaurant.addResponsible(user);
    }

}
