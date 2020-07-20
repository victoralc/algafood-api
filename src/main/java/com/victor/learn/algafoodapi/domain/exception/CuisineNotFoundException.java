package com.victor.learn.algafoodapi.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;
    private static final String CUISINE_NOT_FOUND_MESSAGE = "Cuisine was not found with id %d";

    public CuisineNotFoundException(Long cityId) {
        super(String.format(CUISINE_NOT_FOUND_MESSAGE, cityId));
    }
    
    public CuisineNotFoundException(String message) {
        super(message);
    }
}
