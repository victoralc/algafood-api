package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.PaymentTypeNotFoundException;
import com.victor.learn.algafoodapi.domain.model.PaymentType;
import com.victor.learn.algafoodapi.domain.repository.PaymentTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentTypeService {

    private static final String PAYMENT_TYPE_IN_USE_MESSAGE = "Payment type code %d cannot be removed. It is already in use.";

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public PaymentType findById(Long paymentTypeId) {
        return paymentTypeRepository.findById(paymentTypeId)
                .orElseThrow(() -> new PaymentTypeNotFoundException(paymentTypeId));
    }

    @Transactional
    public PaymentType save(PaymentType paymentType) {
        return paymentTypeRepository.save(paymentType);
    }

    @Transactional
    public void remove(Long paymentTypeId) {
        try {
            paymentTypeRepository.deleteById(paymentTypeId);
            paymentTypeRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentTypeNotFoundException(paymentTypeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(PAYMENT_TYPE_IN_USE_MESSAGE, paymentTypeId));
        }
    }


}
