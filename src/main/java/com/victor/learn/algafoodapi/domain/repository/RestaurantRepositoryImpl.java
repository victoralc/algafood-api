package com.victor.learn.algafoodapi.domain.repository;

import com.victor.learn.algafoodapi.domain.model.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Restaurant> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        
        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);

        Predicate nomePredicate = criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        Predicate taxaInicialPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
        Predicate taxaFinallPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
        
        criteriaQuery.where(nomePredicate, taxaInicialPredicate, taxaFinallPredicate);

        TypedQuery<Restaurant> typedQuery = entityManager.createQuery(criteriaQuery);
        
        return typedQuery.getResultList();
    }
    
}
