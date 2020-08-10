package com.victor.learn.algafoodapi.api.model.input.state;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StateIdInput {
    
    @NotNull
    private Long id;
    
}
