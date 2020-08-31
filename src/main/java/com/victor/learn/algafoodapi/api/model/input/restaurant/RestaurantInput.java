package com.victor.learn.algafoodapi.api.model.input.restaurant;

import com.victor.learn.algafoodapi.api.model.input.address.AddressInput;
import com.victor.learn.algafoodapi.api.model.input.cuisine.CuisineIdInput;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantInput {

    @NotNull
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal deliveryTax;
    
    @Valid
    @NotNull
    private CuisineIdInput cuisine;

    private Boolean active = Boolean.TRUE;

    @Valid
    @NotNull
    private AddressInput address;
}
