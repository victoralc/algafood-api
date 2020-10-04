package com.victor.learn.algafoodapi.domain.repository.specs;

import com.victor.learn.algafoodapi.domain.model.Order;
import com.victor.learn.algafoodapi.domain.repository.filter.OrderFilter;
import lombok.var;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            root.fetch("restaurant").fetch("cuisine");
            root.fetch("customer");

            var predicates = new ArrayList<Predicate>();

            if (filter.getCustomerId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("customer"), filter.getCustomerId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getDateCreationBegin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"),
                        filter.getDateCreationBegin()));
            }

            if (filter.getDateCreationEnd() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"),
                        filter.getDateCreationEnd()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
