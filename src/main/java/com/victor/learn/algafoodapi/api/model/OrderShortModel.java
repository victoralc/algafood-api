package com.victor.learn.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.victor.learn.algafoodapi.domain.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderShortModel {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal deliveryTax;
    private BigDecimal total;
    private String status;
    private OffsetDateTime creationDate;
    private OffsetDateTime deliveryDate;
    private RestaurantShortModel restaurant;
    private UserModel customer;

}
