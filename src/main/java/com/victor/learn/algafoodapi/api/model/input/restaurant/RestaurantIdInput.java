package com.victor.learn.algafoodapi.api.model.input.restaurant;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RestaurantIdInput {

    @NotNull
    private Long id;

}
