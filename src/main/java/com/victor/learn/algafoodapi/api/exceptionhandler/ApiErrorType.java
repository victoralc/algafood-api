package com.victor.learn.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {
    
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    INVALID_INPUT("/invalid-input", "Invalid input"),
    MESSAGE_NOT_READABLE("/message-not-readable", "Message not readable"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation"),
    SYSTEM_ERROR("/system-error", "System Error");

    private String uri;
    private String title;
    
    ApiErrorType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
    
}
