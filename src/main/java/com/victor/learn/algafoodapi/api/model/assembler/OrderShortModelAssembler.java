package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.OrderModel;
import com.victor.learn.algafoodapi.api.model.OrderShortModel;
import com.victor.learn.algafoodapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderShortModelAssembler {

    private final ModelMapper modelMapper;

    public OrderShortModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderShortModel toModel(Order order) {
        return this.modelMapper.map(order, OrderShortModel.class);
    }

    public List<OrderShortModel> toCollectionModel(Collection<Order> orders) {
        return orders.stream().map(order -> toModel(order)).collect(toList());
    }

}
