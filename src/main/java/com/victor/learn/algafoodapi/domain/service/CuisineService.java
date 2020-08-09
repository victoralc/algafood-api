package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.CityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.CuisineNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuisineService {

    private static final String CUISINE_IN_USE_MESSAGE = "Cuisine de código %d nao pode ser removida, pois está em uso";

    @Autowired
    private CuisineRepository cuisineRepository;
    
    public Cuisine findById(Long cuisineId) {
        return cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new CuisineNotFoundException(cuisineId));
    }

    @Transactional
    public Cuisine save(Cuisine cuisine) {
        return cuisineRepository.save(cuisine);
    }

    @Transactional
    public void remove(Long cuisineId) {
        try {
            cuisineRepository.deleteById(cuisineId);
        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(cuisineId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(CUISINE_IN_USE_MESSAGE, cuisineId));
        }
    }


}
