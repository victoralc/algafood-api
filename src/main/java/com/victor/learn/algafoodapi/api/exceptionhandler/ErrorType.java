package com.victor.learn.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {
    
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation");

    private String uri;
    private String title;
    
    ErrorType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
    
}
