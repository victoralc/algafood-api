package com.victor.learn.algafoodapi.api.controller;

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

    @GetMapping
    public List<Cuisine> list() {
        return cuisineRepository.findAll();
    }

    @GetMapping("/{cuisineId}")
    public Cuisine find(@PathVariable("cuisineId") Long id) {
        return cuisineService.findById(id);
    }

    @GetMapping("/by-name/{cuisineName}")
    public ResponseEntity<Cuisine> findByName(@PathVariable("cuisineName") String cuisineName) {
        Optional<Cuisine> cuisine = cuisineRepository.findByNameContaining(cuisineName);
        if (cuisine.isPresent()) {
            return ResponseEntity.ok(cuisine.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cuisine> create(@RequestBody @Valid Cuisine cuisine) {
        Cuisine created = cuisineService.save(cuisine);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{cuisineId}")
    public Cuisine update(@PathVariable Long cuisineId, @RequestBody @Valid Cuisine cuisine) {
        Cuisine actual = cuisineService.findById(cuisineId);
        BeanUtils.copyProperties(cuisine, actual, "id");
        return cuisineRepository.save(actual);
    }

    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long cuisineId) { 
        cuisineService.remove(cuisineId);
    }

}
