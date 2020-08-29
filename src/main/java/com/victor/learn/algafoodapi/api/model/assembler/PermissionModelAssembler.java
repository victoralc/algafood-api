package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.PermissionModel;
import com.victor.learn.algafoodapi.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PermissionModelAssembler {

    private final ModelMapper modelMapper;

    public PermissionModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PermissionModel toModel(Permission permission) {
        return this.modelMapper.map(permission, PermissionModel.class);
    }

    public List<PermissionModel> toCollectionModel(List<Permission> permissions) {
        return permissions.stream().map(permission -> toModel(permission)).collect(toList());
    }

}
