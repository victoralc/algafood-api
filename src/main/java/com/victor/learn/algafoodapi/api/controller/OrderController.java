package com.victor.learn.algafoodapi.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.victor.learn.algafoodapi.api.model.OrderModel;
import com.victor.learn.algafoodapi.api.model.OrderShortModel;
import com.victor.learn.algafoodapi.api.model.assembler.OrderInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.OrderModelAssembler;
import com.victor.learn.algafoodapi.api.model.assembler.OrderShortModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.order.OrderInput;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Order;
import com.victor.learn.algafoodapi.domain.model.User;
import com.victor.learn.algafoodapi.domain.repository.OrderRepository;
import com.victor.learn.algafoodapi.domain.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderModelAssembler orderModelAssembler;
    private final OrderShortModelAssembler orderShortModelAssembler;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderInputDisassembler orderInputDisassembler;

    public OrderController(OrderModelAssembler orderModelAssembler, OrderShortModelAssembler orderShortModelAssembler, OrderService orderService, OrderRepository orderRepository, OrderInputDisassembler orderInputDisassembler) {
        this.orderModelAssembler = orderModelAssembler;
        this.orderShortModelAssembler = orderShortModelAssembler;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderInputDisassembler = orderInputDisassembler;
    }

    /*@GetMapping
    public List<OrderShortModel> list() {
        final List<Order> orders = orderRepository.findAll();
        return orderShortModelAssembler.toCollectionModel(orders);
    }*/

    @GetMapping
    public MappingJacksonValue list(@RequestParam(required = false) String fields) {
        final List<Order> orders = orderRepository.findAll();
        final List<OrderShortModel> orderShortModels = orderShortModelAssembler.toCollectionModel(orders);

        MappingJacksonValue jacksonViewWrapper = new MappingJacksonValue(orderShortModels);

        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.hasText(fields)) {
            simpleFilterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
        }

        jacksonViewWrapper.setFilters(simpleFilterProvider);

        return jacksonViewWrapper;
    }

    @GetMapping("/{orderId}")
    public OrderModel find(@PathVariable Long orderId) {
        final Order order = orderService.find(orderId);
        return orderModelAssembler.toModel(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel add(@Valid @RequestBody OrderInput orderInput) {
        try {
            Order newOrder = orderInputDisassembler.toDomainObject(orderInput);

            // TODO pegar usuário autenticado
            newOrder.setCustomer(new User());
            newOrder.getCustomer().setId(1L);

            newOrder = orderService.create(newOrder);

            return orderModelAssembler.toModel(newOrder);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

}
