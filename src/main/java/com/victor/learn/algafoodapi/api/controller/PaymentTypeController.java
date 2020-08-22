package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.PaymentTypeModel;
import com.victor.learn.algafoodapi.api.model.assembler.PaymentTypeInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.PaymentTypeModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.paymenttype.PaymentTypeInput;
import com.victor.learn.algafoodapi.domain.model.PaymentType;
import com.victor.learn.algafoodapi.domain.repository.PaymentTypeRepository;
import com.victor.learn.algafoodapi.domain.service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payment-type")
public class PaymentTypeController {

    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeService paymentTypeService;
    private final PaymentTypeModelAssembler paymentTypeModelAssembler;
    private final PaymentTypeInputDisassembler paymentTypeInputDisassembler;

    public PaymentTypeController(PaymentTypeRepository paymentTypeRepository, PaymentTypeService paymentTypeService, PaymentTypeModelAssembler paymentTypeModelAssembler, PaymentTypeInputDisassembler paymentTypeInputDisassembler) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeService = paymentTypeService;
        this.paymentTypeModelAssembler = paymentTypeModelAssembler;
        this.paymentTypeInputDisassembler = paymentTypeInputDisassembler;
    }

    @GetMapping
    public List<PaymentTypeModel> listAll() {
        List<PaymentType> allPaymentTypes = paymentTypeRepository.findAll();
        return paymentTypeModelAssembler.toCollectionModel(allPaymentTypes);
    }

    @GetMapping("/{paymentTypeId}")
    public PaymentTypeModel find(@PathVariable Long paymentTypeId) {
        final PaymentType paymentType = paymentTypeService.findById(paymentTypeId);
        return paymentTypeModelAssembler.toModel(paymentType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentTypeModel add(@RequestBody @Valid PaymentTypeInput paymentTypeInput) {

        PaymentType paymentType = paymentTypeInputDisassembler.toDomainObject(paymentTypeInput);
        paymentType = paymentTypeService.save(paymentType);
        return paymentTypeModelAssembler.toModel(paymentType);
    }

    @PutMapping("/{paymentTypeId}")
    public PaymentTypeModel update(@PathVariable Long paymentTypeId,
                                         @RequestBody @Valid PaymentTypeInput paymentTypeInput) {

        PaymentType paymentType = paymentTypeService.findById(paymentTypeId);
        paymentTypeInputDisassembler.copyToDomainObject(paymentTypeInput, paymentType);
        paymentType = paymentTypeService.save(paymentType);
        return paymentTypeModelAssembler.toModel(paymentType);
    }

    @DeleteMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long paymentTypeId) {
        paymentTypeService.remove(paymentTypeId);
    }
}
