package com.victor.learn.algafoodapi.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;
    private static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurant was not found with id %d";

    public RestaurantNotFoundException(Long cityId) {
        super(String.format(RESTAURANT_NOT_FOUND_MESSAGE, cityId));
    }
    
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
