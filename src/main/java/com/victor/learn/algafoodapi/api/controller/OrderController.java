package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.OrderModel;
import com.victor.learn.algafoodapi.api.model.ProductModel;
import com.victor.learn.algafoodapi.api.model.assembler.OrderModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.product.ProductInput;
import com.victor.learn.algafoodapi.domain.model.Order;
import com.victor.learn.algafoodapi.domain.model.Product;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.repository.OrderRepository;
import com.victor.learn.algafoodapi.domain.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderModelAssembler orderModelAssembler;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderModelAssembler orderModelAssembler, OrderService orderService, OrderRepository orderRepository) {
        this.orderModelAssembler = orderModelAssembler;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<OrderModel> list() {
        final List<Order> orders = orderRepository.findAll();
        return orderModelAssembler.toCollectionModel(orders);
    }

    @GetMapping("/{orderId}")
    public OrderModel find(@PathVariable Long orderId) {
        final Order order = orderService.find(orderId);
        return orderModelAssembler.toModel(order);
    }

}
