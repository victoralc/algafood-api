package com.victor.learn.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends RuntimeException {
    
    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
    
}
