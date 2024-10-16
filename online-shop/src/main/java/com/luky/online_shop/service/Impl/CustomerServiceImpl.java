package com.luky.online_shop.service.Impl;

import com.luky.online_shop.dto.request.SearchCustomerRequest;
import com.luky.online_shop.entity.Customer;
import com.luky.online_shop.repository.CustomerRepository;
import com.luky.online_shop.service.CustomerService;
import com.luky.online_shop.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Customer> getAll(SearchCustomerRequest searchCustomerRequest) {
        Specification<Customer> customerSpecification = CustomerSpecification.getSpecification(searchCustomerRequest);
        if(searchCustomerRequest.getName() == null && searchCustomerRequest.getPhone() == null && searchCustomerRequest.getBirthDate() == null && searchCustomerRequest.getStatus() == null){
            return customerRepository.findAll();
        }
        return customerRepository.findAll(customerSpecification);
    }

    @Override
    public Customer update(Customer customer) {
        findByIdOrThrowNotFound(customer.getId());
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public void deleteById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        customerRepository.delete(customer);
    }

    public Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("customer not found"));
    }

    @Override
    public void updateStatusById(String id, Boolean status){
        findByIdOrThrowNotFound(id);
        customerRepository.updateStatus(id,status);
    }
}
