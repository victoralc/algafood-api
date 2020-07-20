package com.victor.learn.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String mensagem) {
        super(mensagem);
    }

    public EntityNotFoundException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
