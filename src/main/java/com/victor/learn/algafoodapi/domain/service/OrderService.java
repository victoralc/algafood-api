package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.CuisineNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.OrderNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.model.Order;
import com.victor.learn.algafoodapi.domain.repository.CuisineRepository;
import com.victor.learn.algafoodapi.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order find(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

}
