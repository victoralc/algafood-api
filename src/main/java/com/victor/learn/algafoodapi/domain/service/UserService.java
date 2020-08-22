package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.UserNotFoundException;
import com.victor.learn.algafoodapi.domain.model.User;
import com.victor.learn.algafoodapi.domain.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UserService {

    private final GroupService groupService;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
    }

    public void disassociate(Long userId, Long groupId) {

    }

    @Transactional
    public User save(User user) {
        userRepository.detach(user);
        final Optional<User> userExistent = userRepository.findByEmail(user.getEmail());
        if (userExistent.isPresent() && !userExistent.get().equals(user)) {
            throw new BusinessException(String.format("User with email %s already exist", user.getEmail()));
        }
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = findOrFail(userId);

        if (user.passwordNotEqualTo(currentPassword)) {
            throw new BusinessException("Password provided is not equal to user password");
        }

        user.setPassword(newPassword);
    }

    public User findOrFail(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

}
