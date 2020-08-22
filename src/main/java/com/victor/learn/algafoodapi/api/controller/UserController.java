package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.UserModel;
import com.victor.learn.algafoodapi.api.model.assembler.UserInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.UserModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.password.PasswordInput;
import com.victor.learn.algafoodapi.api.model.input.user.UserInput;
import com.victor.learn.algafoodapi.domain.model.User;
import com.victor.learn.algafoodapi.domain.repository.UserRepository;
import com.victor.learn.algafoodapi.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final UserInputDisassembler userInputDisassembler;

    public UserController(UserRepository userRepository, UserService userService, UserModelAssembler userModelAssembler, UserInputDisassembler userInputDisassembler) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.userInputDisassembler = userInputDisassembler;
    }

    @GetMapping
    public List<UserModel> listAll() {
        final List<User> allUsers = userRepository.findAll();
        return userModelAssembler.toCollectionModel(allUsers);
    }

    @GetMapping("/userId")
    public UserModel find(@PathVariable Long userId) {
        final User user = userService.findOrFail(userId);
        return userModelAssembler.toModel(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel add(@RequestBody @Valid UserInput userInput) {
        User user = userInputDisassembler.toDomainObject(userInput);
        user = userService.save(user);
        return userModelAssembler.toModel(user);
    }

    @PutMapping("/{userId}")
    public UserModel update(@PathVariable Long userId,
                            @RequestBody @Valid UserInput userInput) {
        User user = userService.findOrFail(userId);
        userInputDisassembler.copyToDomainObject(userInput, user);
        user = userService.save(user);
        return userModelAssembler.toModel(user);
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long userId, @RequestBody @Valid PasswordInput password) {
        userService.changePassword(userId, password.getCurrentPassword(), password.getNewPassword());
    }

}
