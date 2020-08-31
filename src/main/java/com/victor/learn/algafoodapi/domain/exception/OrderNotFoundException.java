package com.victor.learn.algafoodapi.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order was not found with id %d";

    public OrderNotFoundException(Long orderId) {
        super(String.format(ORDER_NOT_FOUND_MESSAGE, orderId));
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
