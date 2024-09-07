package com.example.wheycenter.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class S3Config {

    private static final String CREDENTIALS_FILE_PATH = "static/rootkey.csv";

    @Value("${aws.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        Map<String, String> credentials = loadAwsCredentials();
        String accessKeyId = credentials.get("accessKeyId");
        String secretAccessKey = credentials.get("secretAccessKey");

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    private Map<String, String> loadAwsCredentials() {
        Map<String, String> credentials = new HashMap<>();
        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(CREDENTIALS_FILE_PATH).getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                credentials.put("accessKeyId", record.get("Access key ID"));
                credentials.put("secretAccessKey", record.get("Secret access key"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load AWS credentials");
        }
        return credentials;
    }
}
