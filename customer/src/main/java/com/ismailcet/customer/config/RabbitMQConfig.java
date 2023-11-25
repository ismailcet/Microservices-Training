package com.ismailcet.customer.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${customer.rabbitmq.exchange}")
    String exchange;
    @Value("${customer.rabbitmq.email}")
    String email;
    @Value("${customer.rabbitmq.sms}")
    String sms;
    @Value("${customer.rabbitmq.routingKey}")
    String routingKey;

    @Bean
    FanoutExchange exchange(){
        return new FanoutExchange(exchange);
    }
    @Bean
    Queue emailQueue(){
        return new Queue(email,true);
    }
    @Bean
    Queue smsQueue(){
        return new Queue(sms,true);
    }
    @Bean
    Binding bindingEmail(Queue emailQueue, FanoutExchange exchange){
        return BindingBuilder.bind(emailQueue).to(exchange);
    }
    @Bean
    Binding bindingSms(Queue smsQueue, FanoutExchange exchange){
        return BindingBuilder.bind(smsQueue).to(exchange);
    }
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
