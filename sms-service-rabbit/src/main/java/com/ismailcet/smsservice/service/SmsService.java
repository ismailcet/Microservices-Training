package com.ismailcet.smsservice.service;

import com.ismailcet.smsservice.entity.Customer;
import com.ismailcet.smsservice.entity.Sms;
import com.ismailcet.smsservice.repository.SmsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class SmsService {
    private final SmsRepository smsRepository;
    public SmsService(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    @RabbitListener(queues = "${customer.rabbitmq.sms}")
    public void sendSmsAndSaveDB(Customer event){
      log.info("REGISTRATION SMS: {}", event);
        Sms sms = Sms.builder()
                .customerId(event.getId())
                .customerName(event.getFirstName())
                .createdAt(LocalDateTime.now())
                .information("Rabbit")
                .build();
        smsRepository.save(sms);
    }
}
