package com.example.onlinestorespring.specification;

import com.example.onlinestorespring.dto.ProductSearchCriteria;
import com.example.onlinestorespring.model.Product;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    private ProductSearchCriteria criteria;

    public ProductSpecification(ProductSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public @Nullable Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();

        if (criteria.getTitle() != null && !criteria.getTitle().isBlank()) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + criteria.getTitle() + "%"));
        }

        if (criteria.getMinPrice() != null && criteria.getMinPrice() > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
        }

        if (criteria.getMaxPrice() != null && criteria.getMaxPrice() > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
