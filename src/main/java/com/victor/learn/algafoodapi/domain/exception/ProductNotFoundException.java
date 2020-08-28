package com.victor.learn.algafoodapi.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product was not found with id %d";

    public ProductNotFoundException(Long productId) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
