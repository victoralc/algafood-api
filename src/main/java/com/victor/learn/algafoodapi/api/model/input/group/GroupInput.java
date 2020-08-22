package com.victor.learn.algafoodapi.api.model.input.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class GroupInput {

    @NotBlank
    private String name;

}
