package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.CuisineModel;
import com.victor.learn.algafoodapi.api.model.assembler.CuisineInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.CuisineModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.cuisine.CuisineInput;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.repository.CuisineRepository;
import com.victor.learn.algafoodapi.domain.service.CuisineService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private CuisineService cuisineService;

    @Autowired
    private CuisineModelAssembler cuisineModelAssembler;

    @Autowired
    private CuisineInputDisassembler cuisineInputDisassembler;

    @GetMapping
    public List<CuisineModel> list() {
        List<Cuisine> cuisines = cuisineRepository.findAll();
        return cuisineModelAssembler.toCollectionModel(cuisines);
    }

    @GetMapping("/{cuisineId}")
    public CuisineModel find(@PathVariable("cuisineId") Long id) {
        Cuisine cuisine = cuisineService.findById(id);
        return cuisineModelAssembler.toModel(cuisine);
    }

    @GetMapping("/by-name/{cuisineName}")
    public CuisineModel findByName(@PathVariable("cuisineName") String cuisineName) {
        Optional<Cuisine> cuisine = cuisineRepository.findByNameContaining(cuisineName);
        if (cuisine.isPresent()) {
            return cuisineModelAssembler.toModel(cuisine.get());
        }
        throw new EntityNotFoundException("City not found with name " + cuisineName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineModel create(@RequestBody @Valid CuisineInput cuisineInput) {
        Cuisine cuisine = cuisineInputDisassembler.toDomainObject(cuisineInput);
        cuisine = cuisineService.save(cuisine);
        return cuisineModelAssembler.toModel(cuisine);
    }

    @PutMapping("/{cuisineId}")
    public CuisineModel update(@PathVariable Long cuisineId, @RequestBody @Valid CuisineInput cuisineInput) {
        Cuisine actual = cuisineService.findById(cuisineId);
        cuisineInputDisassembler.copyToDomainObject(cuisineInput, actual);
        actual = cuisineRepository.save(actual);
        return cuisineModelAssembler.toModel(actual);
    }

    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long cuisineId) {
        cuisineService.remove(cuisineId);
    }

}
