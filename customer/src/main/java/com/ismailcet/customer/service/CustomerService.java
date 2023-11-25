package com.ismailcet.customer.service;

import com.ismailcet.customer.client.FraudCheckResponse;
import com.ismailcet.customer.client.FraudServiceClient;
import com.ismailcet.customer.controller.Request.CustomerRegistrationRequest;
import com.ismailcet.customer.entity.Customer;
import com.ismailcet.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudServiceClient  fraudServiceClient;
    private final FanoutExchange exchange;
    private final AmqpTemplate rabbitTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${spring.kafka.producer.topic}")
    private String kafkaTopic;

    public CustomerService(CustomerRepository customerRepository, FraudServiceClient fraudServiceClient, FanoutExchange exchange, RabbitTemplate rabbitTemplate, KafkaTemplate<String, Object> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.fraudServiceClient = fraudServiceClient;
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse =
                fraudServiceClient.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
        // todo send notication with RabbitMq
        rabbitTemplate.convertAndSend(exchange.getName(),"",customer);

        // todo send notification with Kafka
        kafkaTemplate.send(kafkaTopic, customer);
    }
}
