package com.victor.learn.algafoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderItem {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer quantity;
    private String notes;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;
}
