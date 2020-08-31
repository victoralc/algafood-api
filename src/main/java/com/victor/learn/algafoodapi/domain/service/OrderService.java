package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.OrderNotFoundException;
import com.victor.learn.algafoodapi.domain.model.*;
import com.victor.learn.algafoodapi.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CityService cityService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final PaymentTypeService paymentTypeService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CityService cityService, UserService userService, RestaurantService restaurantService, PaymentTypeService paymentTypeService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cityService = cityService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.paymentTypeService = paymentTypeService;
        this.productService = productService;
    }

    public Order find(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public Order create(Order order) {
        validateOrder(order);
        validateItems(order);

        order.setDeliveryTax(order.getRestaurant().getDeliveryTax());
        order.calculateTotal();

        return orderRepository.save(order);
    }

    private void validateOrder(Order order) {
        City city = cityService.findById(order.getDeliveryAddress().getCity().getId());
        User customer = userService.findOrFail(order.getCustomer().getId());
        Restaurant restaurant = restaurantService.findOrFail(order.getRestaurant().getId());
        PaymentType paymentType = paymentTypeService.findById(order.getPaymentType().getId());

        order.getDeliveryAddress().setCity(city);
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setPaymentType(paymentType);

        if (restaurant.notAcceptPaymentType(paymentType)) {
            throw new BusinessException(String.format("Payment type '%s' is not acceptable by this restaurant.",
                    paymentType.getDescription()));
        }
    }

    private void validateItems(Order order) {
        order.getItems().forEach(item -> {
            final Product product = productService.findById(
                    order.getRestaurant().getId(), item.getProduct().getId());

            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }

}
