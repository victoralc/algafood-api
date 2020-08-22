package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.GroupModel;
import com.victor.learn.algafoodapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class GroupModelAssembler {

    private final ModelMapper modelMapper;

    public GroupModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupModel toModel(Group group) {
        return this.modelMapper.map(group, GroupModel.class);
    }

    public List<GroupModel> toCollectionModel(Collection<Group> groups) {
        return groups.stream().map(group -> toModel(group)).collect(toList());
    }

}
