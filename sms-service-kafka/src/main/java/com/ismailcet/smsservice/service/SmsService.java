package com.ismailcet.smsservice.service;

import com.ismailcet.smsservice.entity.Customer;
import com.ismailcet.smsservice.entity.Sms;
import com.ismailcet.smsservice.repository.SmsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class SmsService {
    private final SmsRepository smsRepository;

    public SmsService(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    @KafkaListener(topics = "register-user",groupId = "sms")
    public void registerCustomerListeners(Customer customer){
      log.info("Registered Customer From Kafka :{}",customer);
        Sms sms = Sms.builder()
                .customerId(customer.getId())
                .customerName(customer.getFirstName())
                .createdAt(LocalDateTime.now())
                .information("Kafka")
                .build();
        smsRepository.save(sms);
    }
}
