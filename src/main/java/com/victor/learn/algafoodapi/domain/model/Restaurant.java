package com.victor.learn.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victor.learn.algafoodapi.integration.api.validation.DeliveryTaxFreeOnDescription;
import com.victor.learn.algafoodapi.domain.validations.groups.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DeliveryTaxFreeOnDescription(fieldValue = "deliveryTax", 
                              fieldDescription = "name", 
                              requiredDescription = "Delivery Tax Free")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @PositiveOrZero
    @Column(name = "delivery_tax", nullable = false)
    private BigDecimal deliveryTax;
    
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.CuisineId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;

    @ManyToMany
    @JoinTable(name = "restaurant_payment_type",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
    private List<PaymentType> paymentTypes = new ArrayList<>();

    @JsonIgnore
    @Embedded
    private Address address;
    
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime registerDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime updateDate;
    
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

}
