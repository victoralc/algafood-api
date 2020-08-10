package com.victor.learn.algafoodapi.api.model.input.state;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateInput {

    @NotBlank
    private String name;

}
