package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.group.GroupInput;
import com.victor.learn.algafoodapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupInputDisassembler {

    private final ModelMapper modelMapper;

    public GroupInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Group toDomainObject(GroupInput groupInput) {
        return this.modelMapper.map(groupInput, Group.class);
    }

    public void copyToDomainObject(GroupInput groupInput, Group group) {
        modelMapper.map(groupInput, group);
    }

}
