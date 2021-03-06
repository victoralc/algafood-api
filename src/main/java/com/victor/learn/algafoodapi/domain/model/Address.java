package com.victor.learn.algafoodapi.domain.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class Address {
    
    @Column(name = "address_postal_code")
    private String postalCode;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_additional")
    private String additional;

    @Column(name = "address_neighbour")
    private String neighbour;

    @ManyToOne
    @JoinColumn(name = "address_city_id")
    private City city;
}
