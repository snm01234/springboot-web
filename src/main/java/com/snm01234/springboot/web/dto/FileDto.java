package com.snm01234.springboot.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDto {
    private String uuid; // unique한 파일 이름위한 속성
    private String fileName;
    private String contentType;

    public FileDto(String uuid, String fileName, String contentType) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.contentType = contentType;
        System.out.println(contentType);
    }

}
