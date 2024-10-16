package com.luky.online_shop.service;

import com.luky.online_shop.dto.request.SearchCustomerRequest;
import com.luky.online_shop.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    Customer getById(String id);
    List<Customer> getAll(SearchCustomerRequest searchCustomerRequest);
    Customer update(Customer customer);
    void deleteById(String id);

    void updateStatusById(String id, Boolean status);
}
