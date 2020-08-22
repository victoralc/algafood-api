package com.victor.learn.algafoodapi.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String GROUP_NOT_FOUND_MESSAGE = "Group was not found with id %d";

    public GroupNotFoundException(Long groupId) {
        super(String.format(GROUP_NOT_FOUND_MESSAGE, groupId));
    }
    
    public GroupNotFoundException(String message) {
        super(message);
    }
}
