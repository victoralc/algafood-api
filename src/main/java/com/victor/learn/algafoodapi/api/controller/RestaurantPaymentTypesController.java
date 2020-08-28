package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.PaymentTypeModel;
import com.victor.learn.algafoodapi.api.model.RestaurantModel;
import com.victor.learn.algafoodapi.api.model.assembler.PaymentTypeModelAssembler;
import com.victor.learn.algafoodapi.api.model.assembler.RestaurantInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.RestaurantModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.restaurant.RestaurantInput;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-types")
public class RestaurantPaymentTypesController {

    private final RestaurantService restaurantService;
    private final PaymentTypeModelAssembler paymentTypeModelAssembler;

    public RestaurantPaymentTypesController(PaymentTypeModelAssembler paymentTypeModelAssembler, RestaurantService restaurantService) {
        this.paymentTypeModelAssembler = paymentTypeModelAssembler;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<PaymentTypeModel> listAll(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.findOrFail(restaurantId);
        return paymentTypeModelAssembler.toCollectionModel(restaurant.getPaymentTypes());
    }

    @DeleteMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociatePaymentTypes(@PathVariable Long restaurantId, @PathVariable Long paymentTypeId){
        restaurantService.disassociatePaymentTypes(restaurantId, paymentTypeId);
    }

    @PutMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associatePaymentTypes(@PathVariable Long restaurantId, @PathVariable Long paymentTypeId){
        restaurantService.associatePaymentTypes(restaurantId, paymentTypeId);
    }

}
