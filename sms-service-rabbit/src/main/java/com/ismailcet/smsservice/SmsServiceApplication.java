package com.ismailcet.smsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SmsServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SmsServiceApplication.class, args);
    }
}
