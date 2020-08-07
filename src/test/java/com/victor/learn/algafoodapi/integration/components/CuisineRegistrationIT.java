package com.victor.learn.algafoodapi.integration.components;

import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.service.CuisineService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
class CuisineRegistrationIT {

    @Autowired
    private CuisineService cuisineService;

    @Test
    void shouldRegisterACuisineSuccessfully() {
        Cuisine newCuisine = new Cuisine();
        newCuisine.setName("Chinese");

        newCuisine = cuisineService.save(newCuisine);

        assertThat(newCuisine).isNotNull();
        assertThat(newCuisine.getId()).isNotNull();
    }

    @Test
    void givenACuisineWithoutName_WhenCuisineRegistration_ThenShouldThrowsConstraintViolationException() {
        val cuisine = new Cuisine();
        cuisine.setName("");

        assertThrows(ConstraintViolationException.class, () -> cuisineService.save(cuisine));
    }

    @Test
    void givenACuisineThatNotExists_WhenRemoveCuisine_ThenShouldThrowsEntityNotFoundException() {
        val cuisine = new Cuisine();
        cuisine.setId(1234L);
        cuisine.setName("Brazilian Cuisine");

        assertThrows(EntityNotFoundException.class, () -> cuisineService.remove(cuisine.getId()));
    }

//    @Test
//    void givenACuisineThatIsAlreadyInUse_WhenRemoveCuisine_ThenShouldThrowsEntityInUseException() {
//        assertThrows(EntityInUseException.class, () -> cuisineService.remove(20L));
//    }

}
