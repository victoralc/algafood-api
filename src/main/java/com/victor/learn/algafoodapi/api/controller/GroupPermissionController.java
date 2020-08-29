package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.PermissionModel;
import com.victor.learn.algafoodapi.api.model.assembler.PermissionModelAssembler;
import com.victor.learn.algafoodapi.domain.model.Group;
import com.victor.learn.algafoodapi.domain.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/permissions")
public class GroupPermissionController {

    private final GroupService groupService;

    private final PermissionModelAssembler permissionModelAssembler;

    public GroupPermissionController(GroupService groupService, PermissionModelAssembler permissionModelAssembler) {
        this.groupService = groupService;
        this.permissionModelAssembler = permissionModelAssembler;
    }

    @GetMapping
    public List<PermissionModel> list(@PathVariable Long groupId) {
        final Group group = groupService.findById(groupId);
        return permissionModelAssembler.toCollectionModel(group.getPermissions());
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.removePermission(groupId, permissionId);
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void givePermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.givePermission(groupId, permissionId);
    }


}
