package com.victor.learn.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.victor.learn.algafoodapi.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineModel {

    @JsonView(RestaurantView.Short.class)
    private Long id;

    @JsonView(RestaurantView.Short.class)
    private String name;
}
