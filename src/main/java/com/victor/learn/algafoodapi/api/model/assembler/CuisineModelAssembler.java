package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.CuisineModel;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CuisineModelAssembler {

    private final ModelMapper modelMapper;

    public CuisineModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CuisineModel toModel(Cuisine cuisine) {
        return this.modelMapper.map(cuisine, CuisineModel.class);
    }

    public List<CuisineModel> toCollectionModel(List<Cuisine> cuisines) {
        return cuisines.stream().map(cuisine -> toModel(cuisine)).collect(toList());
    }

}
