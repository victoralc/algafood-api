package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.domain.model.Customer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer home() {
        return new Customer("Victor", "Alcantara", 10);
    }

}
