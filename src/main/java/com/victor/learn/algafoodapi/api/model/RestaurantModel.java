package com.victor.learn.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {
    private Long id;
    private String name;
    private BigDecimal deliveryTax;
    private CuisineModel cuisine;
    private AddressModel address;
    private Boolean active;
    private Boolean open;
}
