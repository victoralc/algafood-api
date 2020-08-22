package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.CityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.GroupNotFoundException;
import com.victor.learn.algafoodapi.domain.model.City;
import com.victor.learn.algafoodapi.domain.model.Group;
import com.victor.learn.algafoodapi.domain.model.State;
import com.victor.learn.algafoodapi.domain.repository.CityRepository;
import com.victor.learn.algafoodapi.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

    private static final String GROUP_IN_USE_MESSAGE
            = "Group with code %d cannot be removed because it is already been used.";

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> listAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    @Transactional
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public void remover(Long groupId) {
        try {
            groupRepository.deleteById(groupId);
            groupRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(GROUP_IN_USE_MESSAGE, groupId));
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(groupId);
        }
    }

}
