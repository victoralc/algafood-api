package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.order.OrderInput;
import com.victor.learn.algafoodapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderInputDisassembler {

    private final ModelMapper modelMapper;

    public OrderInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order toDomainObject(OrderInput orderInput) {
        return this.modelMapper.map(orderInput, Order.class);
    }

    public void copyToDomainObject(OrderInput orderInput, Order order) {
        modelMapper.map(orderInput, order);
    }

}
