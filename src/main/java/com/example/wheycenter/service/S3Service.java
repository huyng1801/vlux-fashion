package com.example.wheycenter.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

@Service
public class S3Service {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(String keyName, InputStream inputStream, long contentLength, String contentType) {
        amazonS3.putObject(new PutObjectRequest(bucketName, keyName, inputStream, null));
        return amazonS3.getUrl(bucketName, keyName).toString();
    }

    public S3Object downloadFile(String keyName) {
        return amazonS3.getObject(bucketName, keyName);
    }

    public void deleteFile(String keyName) {
        amazonS3.deleteObject(bucketName, keyName);
    }
}
