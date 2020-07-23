package com.victor.learn.algafoodapi.domain.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String mensagem) {
        super(mensagem);
    }
}
