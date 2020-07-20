package com.victor.learn.algafoodapi.domain.repository;

import com.victor.learn.algafoodapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("from Restaurant r join fetch r.cuisine left join fetch r.paymentTypes")
    List<Restaurant> findAll();
    
    List<Restaurant> findByDeliveryTaxBetween(BigDecimal initialTax, BigDecimal finalTax);
    
    //@Query("from Restaurant where nome like %:nome% and cuisine.id = :id")
    List<Restaurant> findByName(@Param("name") String name, @Param("id") Long cuisine);

    List<Restaurant> find(String name, BigDecimal initialTax, BigDecimal finalTax);
}
