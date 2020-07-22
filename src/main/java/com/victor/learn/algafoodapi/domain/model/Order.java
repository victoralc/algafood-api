package com.victor.learn.algafoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subtotal;
    private BigDecimal deliveryTax;
    private BigDecimal total;

    @Embedded
    private Address deliveryAddress;

    private OrderStatus status;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private LocalDateTime confirmationDate;
    private LocalDateTime cancelDate;
    private LocalDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_customer_id", nullable = false)
    private User customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

}