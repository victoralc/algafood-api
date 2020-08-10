package com.victor.learn.algafoodapi.api.model.input.city;

import com.victor.learn.algafoodapi.api.model.input.state.StateInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInput {
    
    @NotBlank
    private String name;
    
    @Valid
    @NotNull
    private StateInput state;
}
