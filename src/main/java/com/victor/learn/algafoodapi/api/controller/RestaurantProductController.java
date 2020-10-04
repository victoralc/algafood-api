package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.api.model.ProductModel;
import com.victor.learn.algafoodapi.api.model.RestaurantModel;
import com.victor.learn.algafoodapi.api.model.assembler.ProductInputDisassembler;
import com.victor.learn.algafoodapi.api.model.assembler.ProductModelAssembler;
import com.victor.learn.algafoodapi.api.model.input.product.ProductInput;
import com.victor.learn.algafoodapi.api.model.input.restaurant.RestaurantInput;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.CityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.CuisineNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Product;
import com.victor.learn.algafoodapi.domain.model.Restaurant;
import com.victor.learn.algafoodapi.domain.repository.ProductRepository;
import com.victor.learn.algafoodapi.domain.service.ProductService;
import com.victor.learn.algafoodapi.domain.service.RestaurantService;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final RestaurantService restaurantService;
    private final ProductModelAssembler productModelAssembler;
    private final ProductInputDisassembler productInputDisassembler;

    public RestaurantProductController(ProductModelAssembler productModelAssembler,
                                       ProductService productService,
                                       RestaurantService restaurantService,
                                       ProductRepository productRepository,
                                       ProductInputDisassembler productInputDisassembler) {
        this.restaurantService = restaurantService;
        this.productRepository = productRepository;
        this.productService = productService;
        this.productModelAssembler = productModelAssembler;
        this.productInputDisassembler = productInputDisassembler;
    }

    @GetMapping
    public List<ProductModel> listAll(@PathVariable Long restaurantId, @RequestParam(required = false) boolean includeInactive) {

        final Restaurant restaurant = restaurantService.findOrFail(restaurantId);

        List<Product> allProducts = null;
        if (includeInactive) {
            allProducts = productRepository.findByRestaurant(restaurant);
        } else {
            allProducts = productRepository.findActivesByRestaurant(restaurant);
        }

        return productModelAssembler.toCollectionModel(allProducts);
    }

    @GetMapping("/{productId}")
    public ProductModel findById(@PathVariable Long restaurantId, @PathVariable Long productId) {
        final Product product = productService.findById(restaurantId, productId);
        return productModelAssembler.toModel(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel add(@RequestBody @Valid Long restaurantId,
                               @RequestBody @Valid ProductInput productInput) {
        final Restaurant restaurant = restaurantService.findOrFail(restaurantId);

        Product product = productInputDisassembler.toDomainObject(productInput);
        product.setRestaurant(restaurant);

        product = productService.save(product);

        return productModelAssembler.toModel(product);
    }

    @PutMapping("/{productId}")
    public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId,
                                  @RequestBody @Valid ProductInput productInput) {
        Product product = productService.findById(restaurantId, productId);

        productInputDisassembler.copyToDomainObject(productInput, product);

        product = productService.save(product);

        return productModelAssembler.toModel(product);
    }

}
