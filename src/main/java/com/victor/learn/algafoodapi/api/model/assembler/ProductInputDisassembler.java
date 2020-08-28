package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.input.product.ProductInput;
import com.victor.learn.algafoodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductInputDisassembler {

    private final ModelMapper modelMapper;

    public ProductInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toDomainObject(ProductInput productInput) {
        return modelMapper.map(productInput, Product.class);
    }

    public void copyToDomainObject(ProductInput productInput, Product product) {
        modelMapper.map(productInput, product);
    }
}
