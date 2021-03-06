package com.victor.learn.algafoodapi.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.victor.learn.algafoodapi.api.model.assembler.RestaurantInputDisassembler;
import com.victor.learn.algafoodapi.api.model.RestaurantModel;
import com.victor.learn.algafoodapi.api.model.assembler.RestaurantModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.restaurant.RestaurantInput;
import com.victor.learn.algafoodapi.api.model.view.RestaurantView;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.CityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.CuisineNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.repository.RestaurantRepository;
import com.victor.learn.algafoodapi.domain.service.RestaurantService;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

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

    @JsonView(RestaurantView.Short.class)
    @GetMapping
    public List<RestaurantModel> list() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantModelAssembler.toCollectionModel(restaurants);
    }

    @JsonView(RestaurantView.OnlyName.class)
    @GetMapping(params = "view=only-name")
    public List<RestaurantModel> listRestaurantOnlyWithName() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantModelAssembler.toCollectionModel(restaurants);
    }

    /*@GetMapping
    public MappingJacksonValue listAll(@RequestParam(required = false) String view) {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantModel> restaurantModel = restaurantModelAssembler.toCollectionModel(restaurants);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(restaurantModel);
        mappingJacksonValue.setSerializationView(RestaurantView.Short.class);

        if ("only-name".equals(view)) {
            mappingJacksonValue.setSerializationView(RestaurantView.OnlyName.class);
        } else if ("all-fields".equals(view)) {
            mappingJacksonValue.setSerializationView(null);
        }

        return mappingJacksonValue;
    }*/

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
        restaurantInputDisassembler.copyToDomainObject(restaurantInput, actual);
        try {
            return restaurantModelAssembler.toModel(restaurantService.save(actual));
        } catch (CuisineNotFoundException | CityNotFoundException e) {
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

    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long restaurantId) {
        restaurantService.activate(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long restaurantId) {
        restaurantService.inactivate(restaurantId);
    }

    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restaurantId) {
        restaurantService.open(restaurantId);
    }

    @PutMapping("/{restaurantId}/closing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable Long restaurantId) {
        restaurantService.close(restaurantId);
    }
}
