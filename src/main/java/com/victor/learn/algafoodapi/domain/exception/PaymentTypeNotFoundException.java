package com.victor.learn.algafoodapi.domain.exception;

public class PaymentTypeNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String PAYMENT_TYPE_NOT_FOUND_MESSAGE = "Payment Type was not found with id %d";

    public PaymentTypeNotFoundException(Long paymentTypeId) {
        super(String.format(PAYMENT_TYPE_NOT_FOUND_MESSAGE, paymentTypeId));
    }

    public PaymentTypeNotFoundException(String message) {
        super(message);
    }
}
