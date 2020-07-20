package com.victor.learn.algafoodapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.repository.RestaurantRepository;
import com.victor.learn.algafoodapi.domain.service.RestaurantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> listar() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> buscarPorId(@PathVariable Long restaurantId) {
        Optional<Restaurant> found = restaurantRepository.findById(restaurantId);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Restaurant restaurant) {
        try {
            restaurant = restaurantService.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public Restaurant update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        Restaurant actual = restaurantService.findOrFail(restaurantId);
        BeanUtils.copyProperties(restaurant, actual, "id", "paymentType", "address", "creationDate", "products");
        try {
            return restaurantService.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields) {
        Restaurant actual = restaurantService.findOrFail(restaurantId);
        merge(fields, actual);
        return update(restaurantId, actual);
    }

    @GetMapping("/between-delivery-taxes/{initialTax}/{finalTax}")
    public ResponseEntity<?> findBetweenTaxes(@PathVariable BigDecimal initialTax, @PathVariable BigDecimal finalTax) {
        List<Restaurant> restaurants = restaurantRepository.findByDeliveryTaxBetween(initialTax, finalTax);
        if (!restaurants.isEmpty()) {
            return ResponseEntity.ok(restaurants);
        }
        return ResponseEntity.notFound().build();
    }

    private void merge(Map<String, Object> fields, Restaurant target) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant origin = objectMapper.convertValue(fields, Restaurant.class);
        
        fields.forEach((propertyName, propertyValue) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, origin);
            ReflectionUtils.setField(field, target, newValue);
        });

    }
}
