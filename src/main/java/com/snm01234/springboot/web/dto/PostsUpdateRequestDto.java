package com.snm01234.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    @NotBlank(message = "제목을 작성해주세요.")
    private String title;
    //@NotBlank(message = "내용을 작성해주세요.")
    private String content;
    private String fileName;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String fileName) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }
}
