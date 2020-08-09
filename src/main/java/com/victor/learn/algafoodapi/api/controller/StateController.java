package com.victor.learn.algafoodapi.api.controller;

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
    private StateService service;

    @GetMapping
    public List<State> list() {
        return this.service.listAll();
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<?> findById(@PathVariable Long stateId) {
        try {
            State found = service.findById(stateId);
            return ResponseEntity.ok(found);
        } catch (StateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<State> create(@RequestBody @Valid State state) {
        state = service.create(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(state);
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<?> update(@PathVariable Long stateId, @RequestBody @Valid State state) {
        try {
            State found = service.findById(stateId);
            BeanUtils.copyProperties(state, found, "id");
            State updated = service.create(found);
            return ResponseEntity.ok(updated);
        } catch (StateNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<State> remove(@PathVariable Long estadoId) {
        try {
            service.remove(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (StateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
