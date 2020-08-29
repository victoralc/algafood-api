package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.PermissionNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Permission;
import com.victor.learn.algafoodapi.domain.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission findOrFail(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
