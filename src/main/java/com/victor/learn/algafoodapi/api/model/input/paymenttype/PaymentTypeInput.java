package com.victor.learn.algafoodapi.api.model.input.paymenttype;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentTypeInput {

    @NotBlank
    private String description;

}
