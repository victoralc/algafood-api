package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.CityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.model.City;
import com.victor.learn.algafoodapi.domain.model.State;
import com.victor.learn.algafoodapi.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    
    private static final String MSG_CIDADE_EM_USO_EXCEPTION = "City de código %d nao pode ser removida, pois está em uso";

    @Autowired
    private CityRepository cityRepository;
    
    @Autowired 
    private StateService stateService;

    public List<City> listAll() {
        return cityRepository.findAll();
    }

    public City findOrFail(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }

    public City save(City city) {
        Long stateId = city.getState().getId();
        State state = stateService.findById(stateId);
        city.setState(state);
        return cityRepository.save(city);
    }
    
    public City findById(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
    }

    public void remover(Long cityId) {
        try {
            cityRepository.deleteById(cityId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_CIDADE_EM_USO_EXCEPTION, cityId));
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(cityId);
        }
    }

}
