package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.RestaurantInputDisassembler;
import com.victor.learn.algafoodapi.api.model.RestaurantModel;
import com.victor.learn.algafoodapi.api.model.RestaurantModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.RestaurantInput;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.repository.RestaurantRepository;
import com.victor.learn.algafoodapi.domain.service.RestaurantService;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;
    private final RestaurantModelAssembler restaurantModelAssembler;
    private final RestaurantInputDisassembler restaurantInputDisassembler;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService, RestaurantModelAssembler restaurantModelAssembler, RestaurantInputDisassembler restaurantInputDisassembler) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
        this.restaurantModelAssembler = restaurantModelAssembler;
        this.restaurantInputDisassembler = restaurantInputDisassembler;
    }

    @GetMapping
    public List<RestaurantModel> listAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return toCollectionModel(restaurants);
    }

    private List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> restaurantModelAssembler.toModel(restaurant)).collect(Collectors.toList());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantModel findById(@PathVariable Long restaurantId) {
        val restaurant = restaurantService.findOrFail(restaurantId);
        return restaurantModelAssembler.toModel(restaurant);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel create(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);
            return restaurantModelAssembler.toModel(restaurantService.save(restaurant));
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public RestaurantModel update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
        Restaurant actual = restaurantService.findOrFail(restaurantId);
        Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);
        BeanUtils.copyProperties(restaurant, actual, "id", "paymentType", "address", "creationDate", "products");
        try {
            return restaurantModelAssembler.toModel(restaurantService.save(restaurant));
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/between-delivery-taxes/{initialTax}/{finalTax}")
    public ResponseEntity<?> findBetweenTaxes(@PathVariable BigDecimal initialTax, @PathVariable BigDecimal finalTax) {
        List<Restaurant> restaurants = restaurantRepository.findByDeliveryTaxBetween(initialTax, finalTax);
        if (!restaurants.isEmpty()) {
            return ResponseEntity.ok(restaurants);
        }
        return ResponseEntity.notFound().build();
    }
}
