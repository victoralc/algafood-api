package com.victor.learn.algafoodapi.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String mensagem) {
        super(mensagem);
    }

    public UserNotFoundException(Long userId) {
        this(String.format("User not found with id %d", userId));
    }
}
