package com.example.wheycenter.config;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class AwsSdkConfig {

    @PostConstruct
    public void init() {
        System.setProperty("aws.java.v1.printLocation", "true");
        System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
    }
}
