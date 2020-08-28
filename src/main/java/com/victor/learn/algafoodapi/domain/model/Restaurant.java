package com.victor.learn.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.victor.learn.algafoodapi.api.validation.DeliveryTaxFreeOnDescription;
import com.victor.learn.algafoodapi.domain.validations.groups.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.CuisineId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurant_payment_type",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
    private Set<PaymentType> paymentTypes = new HashSet<>();

    @JsonIgnore
    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registerDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updateDate;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    private Boolean active = Boolean.TRUE;

    public void activate() {
        setActive(true);
    }

    public void inactivate() {
        setActive(false);
    }

    public boolean removePaymentType(PaymentType paymentType) {
        return this.paymentTypes.remove(paymentType);
    }

    public boolean addPaymentType(PaymentType paymentType) {
        return this.paymentTypes.add(paymentType);
    }

}
