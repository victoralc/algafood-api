package com.victor.learn.algafoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_order")
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

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancelDate;
    private OffsetDateTime deliveryDate;

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

    public void calculateTotal(){
        this.subtotal = getItems().stream().map(orderItem -> orderItem.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void configureDeliveryTax(){
        setDeliveryTax(getRestaurant().getDeliveryTax());
    }

    public void configureOrderItems() {
        getItems().forEach(item -> item.setOrder(this));
    }

}
