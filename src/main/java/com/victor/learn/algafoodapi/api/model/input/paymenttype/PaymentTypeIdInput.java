package com.victor.learn.algafoodapi.api.model.input.paymenttype;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentTypeIdInput {

    @NotNull
    private Long id;

}
