package vn.student.vluxfashion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class AwsSdkConfig {

    @Value("${aws.java.v1.printLocation}")
    private String printLocation;

    @Value("${aws.java.v1.disableDeprecationAnnouncement}")
    private String disableDeprecationAnnouncement;

    @PostConstruct
    public void init() {
        System.setProperty("aws.java.v1.printLocation", printLocation);
        System.setProperty("aws.java.v1.disableDeprecationAnnouncement", disableDeprecationAnnouncement);
    }
}
