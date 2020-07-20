package com.victor.learn.algafoodapi.domain.repository;

import com.victor.learn.algafoodapi.domain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    
}