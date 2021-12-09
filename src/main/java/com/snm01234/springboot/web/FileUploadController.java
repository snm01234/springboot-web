package com.snm01234.springboot.web;

import com.snm01234.springboot.service.FileUploadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/api/v1/upload")
    public String uploadImage(@RequestParam("data") MultipartFile file) {
        return fileUploadService.uploadImage(file);
    }

    @ApiOperation(value = "첨부파일 삭제", notes = "param을 key=fileName, value=파일이름 해서 보내면 삭제 처리")
    @DeleteMapping("/api/v1/deleteFile")
    public void deleteImage(@RequestParam(value = "fileName") String fileName) {
        fileUploadService.deleteImage(fileName);
    }

}