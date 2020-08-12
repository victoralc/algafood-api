package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.CityModel;
import com.victor.learn.algafoodapi.api.model.assembler.CityInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.CityModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.city.CityInput;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.StateNotFoundException;
import com.victor.learn.algafoodapi.domain.model.City;
import com.victor.learn.algafoodapi.domain.service.CityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;
    
    @Autowired
    private CityModelAssembler cityModelAssembler;
    
    @Autowired
    private CityInputDisassembler cityInputDisassembler;

    @GetMapping
    public List<CityModel> list(){
        List<City> cities = this.cityService.listAll();
        return cityModelAssembler.toCollectionModel(cities);
    }

    @GetMapping("/{cityId}")
    public CityModel findById(@PathVariable Long cityId) {
        City city = cityService.findById(cityId);
        return cityModelAssembler.toModel(city);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel create(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDisassembler.toDomainObject(cityInput);
            city = cityService.save(city);
            return cityModelAssembler.toModel(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cityId}")
    public CityModel update(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
        try {
            City cityFound = cityService.findById(cityId);
            cityInputDisassembler.copyToDomainObject(cityInput, cityFound);
            cityFound = cityService.save(cityFound);
            return cityModelAssembler.toModel(cityFound);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cityId}")
    public void delete(@PathVariable Long cityId) {
        cityService.remover(cityId);
    }

}
