package com.victor.learn.algafoodapi.api.model.input.cuisine;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CuisineInput {
    
    @NotBlank
    private String name;
    
}
