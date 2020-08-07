package com.victor.learn.algafoodapi.integration.api;

import com.victor.learn.algafoodapi.domain.model.Cuisine;
import com.victor.learn.algafoodapi.domain.repository.CuisineRepository;
import com.victor.learn.algafoodapi.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application_test.properties")
class CuisineRegistrationApiIT {

    private static final int NONEXISTENT_CUISINE_ID = 200;
    
    @LocalServerPort
    private int port;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cuisines";

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    void shouldReturnHttpStatus200_WhenRequestCuisinesResource() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldContains2Cuisines_WhenRequestCuisinesResource() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(2));
    }

    @Test
    void shouldReturnHttp201Created_WhenCreateNewCuisineIsRequested() {
        given()
                .body("{\"name\": \"Thailand\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldReturnExpectedCuisine_WhenRequestAGetHttpMethodForCuisineById() {
        given()
                .pathParam("cuisineId", 2)
                .accept(ContentType.JSON)
		.when()
                .get("/{cuisineId}")
		.then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Italian"));
    }

    @Test
    void shouldReturn404_WhenRequestAGetHttpMethodForNonexistentCuisineById() {
        given()
                .pathParam("cuisineId", NONEXISTENT_CUISINE_ID)
                .accept(ContentType.JSON)
                .when()
                .get("/{cuisineId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Cuisine cuisine1 = new Cuisine();
        cuisine1.setName("Brazilian");
        cuisineRepository.save(cuisine1);

        Cuisine cuisine2 = new Cuisine();
        cuisine2.setName("Italian");
        cuisineRepository.save(cuisine2);
    }
}
