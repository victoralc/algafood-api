package com.victor.learn.algafoodapi.domain.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class Address {
    
    @Column(name = "address_zipcode")
    private String zipCode;

    @Column(name = "address_name")
    private String address;

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
