package com.victor.learn.algafoodapi.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;
    private static final String CITY_NOT_FOUND_MESSAGE = "City was not found with id %d";

    public CityNotFoundException(Long cityId) {
        super(String.format(CITY_NOT_FOUND_MESSAGE, cityId));
    }
    
    public CityNotFoundException(String message) {
        super(message);
    }
}
