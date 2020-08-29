package com.victor.learn.algafoodapi.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String PERMISSION_NOT_FOUND_MESSAGE = "Permission was not found with id %d";

    public PermissionNotFoundException(Long permissionId) {
        super(String.format(PERMISSION_NOT_FOUND_MESSAGE, permissionId));
    }

    public PermissionNotFoundException(String message) {
        super(message);
    }
}
