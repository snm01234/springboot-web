package com.snm01234.springboot.external;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface UploadService {

    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    void deleteFile(String filePath);

    String getFileUrl(String fileName);

}
