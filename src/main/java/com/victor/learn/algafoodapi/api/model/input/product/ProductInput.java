package com.victor.learn.algafoodapi.api.model.input.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    private Boolean isActive;
}
