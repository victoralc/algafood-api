package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.CuisineNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.ProductNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.model.Product;
import com.victor.learn.algafoodapi.domain.repository.CuisineRepository;
import com.victor.learn.algafoodapi.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private static final String PRODUCT_IN_USE_MESSAGE = "Product with id %d cannot be removed because it's already in use";

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void remove(Long productId) {
        try {
            productRepository.deleteById(productId);
            productRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(productId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(PRODUCT_IN_USE_MESSAGE, productId));
        }
    }


}
