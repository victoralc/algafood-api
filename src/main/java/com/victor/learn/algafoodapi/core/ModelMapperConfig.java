package com.victor.learn.algafoodapi.core;

import com.victor.learn.algafoodapi.api.model.AddressModel;
import com.victor.learn.algafoodapi.domain.model.Address;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        var addressModelTypeMap = modelMapper.createTypeMap(Address.class, AddressModel.class);
        addressModelTypeMap.<String>addMapping(
                address -> address.getCity().getState().getName(),
                (addressModel, value) -> addressModel.getCity().setState(value)
        );
        return modelMapper;
    }

}
