package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.StateModel;
import com.victor.learn.algafoodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class StateModelAssembler {

    private final ModelMapper modelMapper;

    public StateModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StateModel toModel(State state) {
        return this.modelMapper.map(state, StateModel.class);
    }

    public List<StateModel> toCollectionModel(List<State> states) {
        return states.stream().map(state -> toModel(state)).collect(toList());
    }

}
