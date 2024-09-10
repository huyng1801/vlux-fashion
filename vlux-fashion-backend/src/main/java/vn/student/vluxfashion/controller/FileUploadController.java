package vn.student.vluxfashion.controller;

import java.io.InputStream;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.student.vluxfashion.service.AwsS3Service;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private final AwsS3Service s3Service;

    public FileUploadController(AwsS3Service s3Service) {
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
