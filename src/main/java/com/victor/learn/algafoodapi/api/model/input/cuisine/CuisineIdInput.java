package com.victor.learn.algafoodapi.api.model.input.cuisine;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CuisineIdInput {
    
    @NotNull
    private Long id;
    
}
