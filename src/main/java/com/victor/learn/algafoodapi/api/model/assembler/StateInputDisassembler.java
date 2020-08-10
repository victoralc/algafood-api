package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.state.StateInput;
import com.victor.learn.algafoodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StateInputDisassembler {

    private final ModelMapper modelMapper;

    public StateInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public State toDomainObject(StateInput stateInput) {
        return this.modelMapper.map(stateInput, State.class);
    }

    public void copyToDomainObject(StateInput stateInput, State state) {
        modelMapper.map(stateInput, state);
    }

}
