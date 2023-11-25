package com.ismailcet.emailservice.service;

import com.ismailcet.emailservice.entity.Customer;
import com.ismailcet.emailservice.entity.Email;
import com.ismailcet.emailservice.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class EmailService {
    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @KafkaListener(topics = "register-user",groupId = "email")
    public void registerCustomerListeners(Customer customer){
        log.info("Register Customer From Kafka : {}",customer);
        Email email = Email.builder()
                .customerId(customer.getId())
                .email(customer.getEmail())
                .createdDate(LocalDateTime.now())
                .information("Kafka")
                .build();
        emailRepository.save(email);
    }
}
