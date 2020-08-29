package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.UserModel;
import com.victor.learn.algafoodapi.api.model.assembler.UserModelAssembler;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.RestaurantNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/responsible")
public class RestaurantResponsibleUserController {

    private final RestaurantService restaurantService;
    private final UserModelAssembler userModelAssembler;

    public RestaurantResponsibleUserController(RestaurantService restaurantService, UserModelAssembler userModelAssembler) {
        this.restaurantService = restaurantService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public List<UserModel> list(@PathVariable Long restaurantId) {
        final Restaurant restaurant = restaurantService.findOrFail(restaurantId);
        return userModelAssembler.toCollectionModel(restaurant.getResponsible());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long restaurantId, @PathVariable Long userId) {
        try {
            restaurantService.removeResponsible(restaurantId, userId);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@PathVariable Long restaurantId, @PathVariable Long userId) {
        try {
            restaurantService.addResponsible(restaurantId, userId);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

}
