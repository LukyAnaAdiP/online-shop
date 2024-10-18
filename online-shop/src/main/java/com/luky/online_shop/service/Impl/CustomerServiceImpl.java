package com.luky.online_shop.service.Impl;

import com.luky.online_shop.dto.request.SearchCustomerRequest;
import com.luky.online_shop.dto.request.UpdateCustomerRequest;
import com.luky.online_shop.dto.response.CustomerResponse;
import com.luky.online_shop.entity.Customer;
import com.luky.online_shop.entity.UserAccount;
import com.luky.online_shop.repository.CustomerRepository;
import com.luky.online_shop.service.CustomerService;
import com.luky.online_shop.service.UserService;
import com.luky.online_shop.specification.CustomerSpecification;
import com.luky.online_shop.utils.ValidationUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final EntityManager entityManager;

    private final ValidationUtil validationUtil;

    private final UserService userService;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> getAll(SearchCustomerRequest request) {
        Specification<Customer> customerSpecification = CustomerSpecification.getSpecification(request);
        if(request.getName() == null && request.getPhone() == null && request.getBirthDate() == null && request.getStatus() == null){
            return customerRepository.findAll().stream().map(this::parseCustomerToCustomerResponse).toList();
        }
        return customerRepository.findAll(customerSpecification).stream().map(this::parseCustomerToCustomerResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest customerRequest) {
        validationUtil.validate(customerRequest);
        Customer currenCustomer = findByIdOrThrowNotFound(customerRequest.getId());

        UserAccount userAccount = userService.getByContext();

        if(!userAccount.getId().equals(currenCustomer.getUserAccount().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"user not found");
        }

        currenCustomer.setName(customerRequest.getName());
        currenCustomer.setMobilePhoneNo(customerRequest.getMobilePhoneNo());
        currenCustomer.setAddress(customerRequest.getAddress());
        currenCustomer.setBirthDate(Date.valueOf(String.valueOf(customerRequest.getBirthDate())));
        customerRepository.saveAndFlush(currenCustomer);

        return parseCustomerToCustomerResponse(currenCustomer);
    }

    @Override
    public void deleteById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        customerRepository.delete(customer);
    }

    public Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(String id, Boolean status) {
        findByIdOrThrowNotFound(id);
        customerRepository.updateStatus(id,status);
    }

    @Override
    public CustomerResponse getOneById(String id) {
        Customer customerById = findByIdOrThrowNotFound(id);
        return parseCustomerToCustomerResponse(customerById);
    }

    private CustomerResponse parseCustomerToCustomerResponse(Customer customer){
        String userId;
        if(customer.getUserAccount() == null){
            userId = null;
        } else {
            userId = customer.getUserAccount().getId();
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobilePhoneNo(customer.getMobilePhoneNo())
                .address(customer.getAddress())
                .status(customer.getStatus())
                .userAccountId(userId)
                .build();
    }
}
