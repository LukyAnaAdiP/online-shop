package com.luky.online_shop.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luky.online_shop.constant.APIUrl;
import com.luky.online_shop.constant.ResponseMessage;
import com.luky.online_shop.dto.request.SearchCustomerRequest;
import com.luky.online_shop.dto.request.UpdateCustomerRequest;
import com.luky.online_shop.dto.response.CommonResponse;
import com.luky.online_shop.dto.response.CustomerResponse;
import com.luky.online_shop.entity.Customer;
import com.luky.online_shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
	public ResponseEntity<CommonResponse<Customer>> createNewCustomer(@RequestBody Customer customer) {
		Customer newCustomer = customerService.create(customer);
		CommonResponse<Customer> response = CommonResponse.<Customer>builder()
						.statusCode(HttpStatus.CREATED.value())
						.message(ResponseMessage.SUCCESS_SAVE_DATA)
						.data(newCustomer)
						.build();
		return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(response);
	}

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<Customer>> getCustomerById(@PathVariable String id) {
        Customer customerById = customerService.getById(id);
        CommonResponse<Customer> response = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerById)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer(
            @RequestParam(name = "name", required = false ) String name,
            @RequestParam(name = "mobilePhoneNo", required = false) String phone,
            @RequestParam(name = "birthDate", required = false ) @JsonFormat(pattern = "yyyy-MM-dd") String birthDate,
            @RequestParam(name = "status", required = false) Boolean status

    ) {
        SearchCustomerRequest searchCustomerRequest = SearchCustomerRequest.builder()
                .name(name)
                .phone(phone)
                .birthDate(birthDate)
                .status(status)
                .build();

        List<CustomerResponse> customerList = customerService.getAll(searchCustomerRequest);
        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerList)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody UpdateCustomerRequest customer) {
        CustomerResponse updateCustomer = customerService.update(customer);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updateCustomer)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>>  deleteById(@PathVariable String id) {
        customerService.deleteById(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>> updateStatus(
            @PathVariable String id,
            @RequestParam(name = "status") Boolean status
    ){
        customerService.updateStatusById(id, status);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
