package com.luky.online_shop.specification;

import com.luky.online_shop.dto.request.SearchCustomerRequest;
import com.luky.online_shop.entity.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification(SearchCustomerRequest searchCustomerRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchCustomerRequest.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchCustomerRequest.getName().toLowerCase() + "%");
                predicates.add(namePredicate);
            }

            if (searchCustomerRequest.getPhone() != null) {
                Predicate mobilePhoneNoPredicate = criteriaBuilder.equal(root.get("mobilePhoneNo"), searchCustomerRequest.getPhone());
                predicates.add(mobilePhoneNoPredicate);
            }

            if (searchCustomerRequest.getBirthDate() != null){
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate parseDate = LocalDate.parse(searchCustomerRequest.getBirthDate(), dateTimeFormatter);
                Predicate birthDatePredicate = criteriaBuilder.equal(root.get("birthDate"), parseDate);
                predicates.add(birthDatePredicate);

            }

            if(searchCustomerRequest.getStatus() != null) {
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), searchCustomerRequest.getStatus());
                predicates.add(statusPredicate);
            }
            return query.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{}))).getRestriction();
        };
    }
}
