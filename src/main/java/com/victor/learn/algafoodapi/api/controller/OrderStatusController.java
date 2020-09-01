package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.domain.service.OrderStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{orderId}")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable Long orderId) {
        orderStatusService.confirm(orderId);
    }

    @PutMapping("/deliver")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deliver(@PathVariable Long orderId) {
        orderStatusService.confirm(orderId);
    }

    @PutMapping("/cancelation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long orderId) {
        orderStatusService.confirm(orderId);
    }

}
