package com.victor.learn.algafoodapi.api.model.assembler;

import com.victor.learn.algafoodapi.api.model.ProductModel;
import com.victor.learn.algafoodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProductModelAssembler {

    private final ModelMapper modelMapper;

    public ProductModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductModel toModel(Product product) {
        return this.modelMapper.map(product, ProductModel.class);
    }

    public List<ProductModel> toCollectionModel(List<Product> products) {
        return products.stream().map(product -> toModel(product)).collect(toList());
    }

}
