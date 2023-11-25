package com.ismailcet.fraud.controller;

import com.ismailcet.fraud.controller.response.FraudCheckResponse;
import com.ismailcet.fraud.service.FraudCheckHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudCheckHistoryController {
    private final FraudCheckHistoryService fraudCheckHistoryService;

    public FraudCheckHistoryController(FraudCheckHistoryService fraudCheckHistoryService) {
        this.fraudCheckHistoryService = fraudCheckHistoryService;
    }

    @GetMapping("/{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerID){
        FraudCheckResponse response = new FraudCheckResponse();
        boolean isFraudulentCustomer = fraudCheckHistoryService.isFraudulentCustomer(customerID);
        response.setFraudster(isFraudulentCustomer);
        return response;
    }
}
