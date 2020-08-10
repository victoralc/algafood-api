package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.cuisine.CuisineInput;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CuisineInputDisassembler {

    private final ModelMapper modelMapper;

    public CuisineInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cuisine toDomainObject(CuisineInput cuisineInput) {
        return this.modelMapper.map(cuisineInput, Cuisine.class);
    }

    public void copyToDomainObject(CuisineInput cuisineInput, Cuisine cuisine) {
        modelMapper.map(cuisineInput, cuisine);
    }

}
