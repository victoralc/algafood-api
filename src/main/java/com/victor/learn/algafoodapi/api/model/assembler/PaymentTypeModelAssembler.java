package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.PaymentTypeModel;
import com.victor.learn.algafoodapi.domain.model.PaymentType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PaymentTypeModelAssembler {

    private final ModelMapper modelMapper;

    public PaymentTypeModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentTypeModel toModel(PaymentType paymentType) {
        return this.modelMapper.map(paymentType, PaymentTypeModel.class);
    }

    public List<PaymentTypeModel> toCollectionModel(List<PaymentType> paymentTypes) {
        return paymentTypes.stream().map(paymentType -> toModel(paymentType)).collect(toList());
    }

}
