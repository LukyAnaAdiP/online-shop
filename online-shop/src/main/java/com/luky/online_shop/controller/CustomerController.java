package com.luky.online_shop.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luky.online_shop.constant.APIUrl;
import com.luky.online_shop.dto.request.SearchCustomerRequest;
import com.luky.online_shop.entity.Customer;
import com.luky.online_shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public Customer createNewCustomer(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public Customer getCustomerById(@PathVariable String id){
        return customerService.getById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "mobilePhoneNo", required = false) String phone,
            @RequestParam(name = "birthDate", required = false)@JsonFormat(pattern = "yyyy-MM-dd") String birthDate,
            @RequestParam(name = "status", required = false) Boolean status
    ){
        SearchCustomerRequest searchCustomerRequest = SearchCustomerRequest.builder()
                .name(name)
                .phone(phone)
                .birthDate(birthDate)
                .status(status)
                .build();
        return customerService.getAll(searchCustomerRequest);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public String deleteById(@PathVariable String id){
        customerService.deleteById(id);
        return "OK, Success delete customer with id " + id;
    }

    @PutMapping(path = APIUrl.PATH_VAR_ID)
    public String updateStatus(
            @PathVariable String id,
            @RequestParam(name = "status") Boolean status
    ){
        customerService.updateStatusById(id,status);
        return "OK, Success update status";
    }
}
