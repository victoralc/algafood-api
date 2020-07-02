package com.victor.learn.algafoodapi.domain.repository;

import com.victor.learn.algafoodapi.domain.model.Restaurante;
import lombok.var;
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
public class RestauranteRepositoryImpl {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
        
        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);

        Predicate nomePredicate = criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        Predicate taxaInicialPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
        Predicate taxaFinallPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
        
        criteriaQuery.where(nomePredicate, taxaInicialPredicate, taxaFinallPredicate);

        TypedQuery<Restaurante> typedQuery = entityManager.createQuery(criteriaQuery);
        
        return typedQuery.getResultList();
    }
    
}
