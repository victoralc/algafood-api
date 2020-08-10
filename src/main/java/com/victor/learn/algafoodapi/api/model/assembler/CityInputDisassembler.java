package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.city.CityInput;
import com.victor.learn.algafoodapi.api.model.input.state.StateInput;
import com.victor.learn.algafoodapi.domain.model.City;
import com.victor.learn.algafoodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassembler {

    private final ModelMapper modelMapper;

    public CityInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public City toDomainObject(CityInput cityInput) {
        return this.modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInput cityInput, City city) {
        modelMapper.map(cityInput, city);
    }

}
