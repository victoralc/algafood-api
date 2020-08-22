package com.victor.learn.algafoodapi.api.model;

import com.victor.learn.algafoodapi.domain.model.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class AddressModel {

    private String zipCode;
    private String address;
    private String number;
    private String additional;
    private String neighbour;
    private CityNameModel city;

}
