package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.OrderModel;
import com.victor.learn.algafoodapi.api.model.ProductModel;
import com.victor.learn.algafoodapi.domain.model.Order;
import com.victor.learn.algafoodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderModelAssembler {

    private final ModelMapper modelMapper;

    public OrderModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderModel toModel(Order order) {
        return this.modelMapper.map(order, OrderModel.class);
    }

    public List<OrderModel> toCollectionModel(List<Order> orders) {
        return orders.stream().map(order -> toModel(order)).collect(toList());
    }

}
