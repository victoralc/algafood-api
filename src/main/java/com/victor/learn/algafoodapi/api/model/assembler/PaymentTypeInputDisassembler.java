package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.cuisine.CuisineInput;
import com.victor.learn.algafoodapi.api.model.input.paymenttype.PaymentTypeInput;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.model.PaymentType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentTypeInputDisassembler {

    private final ModelMapper modelMapper;

    public PaymentTypeInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentType toDomainObject(PaymentTypeInput paymentTypeInput) {
        return this.modelMapper.map(paymentTypeInput, PaymentType.class);
    }

    public void copyToDomainObject(PaymentTypeInput paymentTypeInput, PaymentType paymentType) {
        modelMapper.map(paymentTypeInput, paymentType);
    }

}
