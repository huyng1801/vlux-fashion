package com.example.wheycenter.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Service
public class S3Service {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(String keyName, InputStream inputStream, long contentLength, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(contentLength);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(putObjectRequest);
        return getUrl(keyName);
    }

    public S3Object downloadFile(String keyName) {
        return amazonS3.getObject(bucketName, keyName);
    }

    public void deleteFile(String keyName) {
        amazonS3.deleteObject(bucketName, keyName);
    }

    public String getUrl(String keyName) {
        URL url = amazonS3.getUrl(bucketName, keyName);
        return url.toString();
    }

    public String generatePresignedUrl(String keyName, int expirationInMinutes) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, keyName)
                .withMethod(com.amazonaws.HttpMethod.GET)
                .withExpiration(getExpirationDate(expirationInMinutes));
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private Date getExpirationDate(int expirationInMinutes) {
        long expirationTime = System.currentTimeMillis() + expirationInMinutes * 60 * 1000;
        return new Date(expirationTime);
    }
}
