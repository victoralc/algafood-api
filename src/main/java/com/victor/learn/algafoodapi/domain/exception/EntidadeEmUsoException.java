package com.victor.learn.algafoodapi.domain.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EntidadeEmUsoException extends RuntimeException {
    
    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
    
}
