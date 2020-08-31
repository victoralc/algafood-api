package com.victor.learn.algafoodapi.api.model.input.order;

import com.victor.learn.algafoodapi.api.model.input.address.AddressInput;
import com.victor.learn.algafoodapi.api.model.input.order_item.OrderItemInput;
import com.victor.learn.algafoodapi.api.model.input.paymenttype.PaymentTypeIdInput;
import com.victor.learn.algafoodapi.api.model.input.restaurant.RestaurantIdInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderInput {
    @Valid
    @NotNull
    private RestaurantIdInput restaurant;

    @Valid
    @NotNull
    private AddressInput deliveryAddress;

    @Valid
    @NotNull
    private PaymentTypeIdInput paymentType;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<OrderItemInput> items;
}
