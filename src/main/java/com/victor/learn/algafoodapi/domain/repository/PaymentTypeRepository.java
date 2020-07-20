package com.victor.learn.algafoodapi.domain.repository;

import com.victor.learn.algafoodapi.domain.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

}
