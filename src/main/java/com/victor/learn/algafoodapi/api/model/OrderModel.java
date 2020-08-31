package com.victor.learn.algafoodapi.api.model;

import com.victor.learn.algafoodapi.domain.model.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderModel {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal deliveryTax;
    private BigDecimal total;
    private String status;
    private OffsetDateTime creationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancelDate;
    private OffsetDateTime deliveryDate;
    private RestaurantShortModel restaurant;
    private UserModel client;
    private PaymentTypeModel paymentType;
    private AddressModel address;
    private List<Order> items = new ArrayList<>();

}
