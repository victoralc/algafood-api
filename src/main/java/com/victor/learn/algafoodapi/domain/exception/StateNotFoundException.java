package com.victor.learn.algafoodapi.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {
    
    private static final long serialVersionUID = 1L;
    private static final String STATE_NOT_FOUND_MESSAGE =
            "State not found for id %d";
    
    public StateNotFoundException(Long stateId){
        this(String.format(STATE_NOT_FOUND_MESSAGE, stateId));
    }

    public StateNotFoundException(String message) {
        super(message);
    }
}
