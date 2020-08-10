package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.StateModel;
import com.victor.learn.algafoodapi.api.model.assembler.StateInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.StateModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.state.StateInput;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.StateNotFoundException;
import com.victor.learn.algafoodapi.domain.model.State;
import com.victor.learn.algafoodapi.domain.service.StateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateService stateService;
    
    @Autowired
    private StateModelAssembler stateModelAssembler;
    
    @Autowired
    private StateInputDisassembler stateInputDisassembler;

    @GetMapping
    public List<StateModel> list() {
        List<State> states = this.stateService.listAll();
        return stateModelAssembler.toCollectionModel(states);
    }

    @GetMapping("/{stateId}")
    public StateModel findById(@PathVariable Long stateId) {
        State stateFound = stateService.findById(stateId);
        return stateModelAssembler.toModel(stateFound);
    }

    @PostMapping
    public StateModel create(@RequestBody @Valid StateInput stateInput) {
        State state = stateInputDisassembler.toDomainObject(stateInput);
        state = stateService.create(state);
        return stateModelAssembler.toModel(state);
    }

    @PutMapping("/{stateId}")
    public StateModel update(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput) {
        State stateFound = stateService.findById(stateId);
        stateInputDisassembler.copyToDomainObject(stateInput, stateFound);
        return stateModelAssembler.toModel(stateFound);
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<State> remove(@PathVariable Long estadoId) {
        try {
            stateService.remove(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (StateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
