package com.victor.learn.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

    private String postalCode;
    private String street;
    private String number;
    private String additional;
    private String neighbour;
    private CityNameModel city;

}
