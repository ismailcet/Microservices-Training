package com.ismailcet.emailservice.service;

import com.ismailcet.emailservice.entity.Customer;
import com.ismailcet.emailservice.entity.Email;
import com.ismailcet.emailservice.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmailService {
    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @RabbitListener(queues = "${customer.rabbitmq.email}")
    public void sendEmailAndSaveDB(Customer event){
      log.info("Register Customer :{}", event);
        Email email = Email.builder()
                .email(event.getEmail())
                .customerId(event.getId())
                .createdDate(LocalDateTime.now())
                .information("RabbitMq")
                .build();
        emailRepository.save(email);
    }
}
