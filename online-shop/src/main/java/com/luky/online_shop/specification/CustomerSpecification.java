package com.luky.online_shop.specification;

import com.luky.online_shop.entity.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification(String name, String phone) {
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }

            if (phone != null && !phone.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("mobilePhoneNo"), phone));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
