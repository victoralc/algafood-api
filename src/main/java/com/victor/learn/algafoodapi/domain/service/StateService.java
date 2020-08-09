package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.StateNotFoundException;
import com.victor.learn.algafoodapi.domain.model.State;
import com.victor.learn.algafoodapi.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    private static final String MSG_ESTADO_EM_USO =
            "State de código %d não pode ser removido, pois está em uso";

    @Autowired
    private StateRepository stateRepository;

    public State findById(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new StateNotFoundException(stateId));
    }

    public List<State> listAll() {
        return stateRepository.findAll();
    }

    @Transactional
    public State create(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public void remove(Long stateId) {
        try {
            stateRepository.deleteById(stateId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_ESTADO_EM_USO, stateId));
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(stateId);
        }
    }

}
