package com.victor.learn.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.victor.learn.algafoodapi.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {

    @JsonView({RestaurantView.Short.class, RestaurantView.OnlyName.class})
    private Long id;

    @JsonView({RestaurantView.Short.class, RestaurantView.OnlyName.class})
    private String name;

    @JsonView(RestaurantView.Short.class)
    private BigDecimal deliveryTax;

    @JsonView(RestaurantView.Short.class)
    private CuisineModel cuisine;

    private AddressModel address;
    private Boolean active;
    private Boolean open;
}
