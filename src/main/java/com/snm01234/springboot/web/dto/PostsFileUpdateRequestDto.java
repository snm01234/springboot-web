package com.snm01234.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsFileUpdateRequestDto {
    private String fileName;

    @Builder
    public PostsFileUpdateRequestDto( String fileName) {
        this.fileName = fileName;
    }

}
