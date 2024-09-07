package com.example.wheycenter.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.wheycenter.service.S3Service;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private final S3Service s3Service;

    @Autowired
    public FileUploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String url = s3Service.uploadFile(fileName, inputStream, file.getSize(), file.getContentType());
            return "File uploaded successfully: " + url;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }
}
