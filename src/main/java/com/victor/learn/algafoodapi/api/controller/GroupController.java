package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.GroupModel;
import com.victor.learn.algafoodapi.api.model.assembler.GroupInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.GroupModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.group.GroupInput;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.StateNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Group;
import com.victor.learn.algafoodapi.domain.repository.GroupRepository;
import com.victor.learn.algafoodapi.domain.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final GroupModelAssembler groupModelAssembler;
    private final GroupInputDisassembler groupInputDisassembler;

    public GroupController(GroupService groupService, GroupRepository groupRepository, GroupModelAssembler groupModelAssembler, GroupInputDisassembler groupInputDisassembler) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.groupModelAssembler = groupModelAssembler;
        this.groupInputDisassembler = groupInputDisassembler;
    }

    @GetMapping
    public List<GroupModel> list() {
        List<Group> groups = this.groupService.listAll();
        return groupModelAssembler.toCollectionModel(groups);
    }

    @GetMapping("/{groupId}")
    public GroupModel findById(@PathVariable Long groupId) {
        final Group group = this.groupService.findById(groupId);
        return groupModelAssembler.toModel(group);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupModel create(@RequestBody @Valid GroupInput groupInput) {
        try {
            Group group = groupInputDisassembler.toDomainObject(groupInput);
            group = groupService.save(group);
            return groupModelAssembler.toModel(group);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{groupId}")
    public GroupModel update(@PathVariable Long groupId, @RequestBody @Valid GroupInput groupInput) {
        try {
            Group group = groupService.findById(groupId);
            groupInputDisassembler.copyToDomainObject(groupInput, group);
            group = groupService.save(group);
            return groupModelAssembler.toModel(group);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{groupId}")
    public void delete(@PathVariable Long groupId) {
        groupService.remover(groupId);
    }

}
