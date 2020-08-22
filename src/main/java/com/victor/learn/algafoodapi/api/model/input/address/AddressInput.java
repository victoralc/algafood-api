package com.victor.learn.algafoodapi.api.model.input.address;

import com.victor.learn.algafoodapi.api.model.input.city.CityIdInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @NotBlank
    private String zipCode;

    @NotBlank
    private String address;

    @NotBlank
    private String number;
    private String additional;

    @NotBlank
    private String neighbour;

    @Valid
    @NotNull
    private CityIdInput city;

}
