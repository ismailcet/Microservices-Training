package com.ismailcet.customer.controller;

import com.ismailcet.customer.controller.Request.CustomerRegistrationRequest;
import com.ismailcet.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/register")
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        log.info("NEW CUSTOMER REGISTRATION {}" , customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);
    }

}
