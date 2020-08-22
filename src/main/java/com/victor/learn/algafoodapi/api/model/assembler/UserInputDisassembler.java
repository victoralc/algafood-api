package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.user.UserInput;
import com.victor.learn.algafoodapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserInputDisassembler {

    private final ModelMapper modelMapper;

    public UserInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toDomainObject(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public void copyToDomainObject(UserInput userInput, User user) {
        modelMapper.map(userInput, user);
    }

}
