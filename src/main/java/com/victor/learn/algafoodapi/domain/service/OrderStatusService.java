package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.model.Order;
import com.victor.learn.algafoodapi.domain.model.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static com.victor.learn.algafoodapi.domain.model.OrderStatus.*;

@Service
public class OrderStatusService {

    private final OrderService orderService;

    public OrderStatusService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void confirm(Long orderId) {
        final Order order = orderService.find(orderId);
        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new BusinessException(String.format("Order with status id %d cannot be changed from %s to %s",
                    order.getId(), order.getStatus().getDescription(), CONFIRMED.getDescription()));
        }

        order.setStatus(CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderService.find(orderId);

        if (!order.getStatus().equals(CREATED)) {
            throw new BusinessException(
                    String.format("Order with status id %d cannot be changed from %s to %s",
                            order.getId(), order.getStatus().getDescription(), CANCELED.getDescription()));
        }

        order.setStatus(CANCELED);
        order.setCancelDate(OffsetDateTime.now());
    }

    @Transactional
    public void deliver(Long orderId) {
        Order order = orderService.find(orderId);

        if (!order.getStatus().equals(CREATED)) {
            throw new BusinessException(
                    String.format("Order with status id %d cannot be changed from %s to %s",
                            order.getId(), order.getStatus().getDescription(), DELIVERED.getDescription()));
        }

        order.setStatus(DELIVERED);
        order.setCancelDate(OffsetDateTime.now());
    }

}
