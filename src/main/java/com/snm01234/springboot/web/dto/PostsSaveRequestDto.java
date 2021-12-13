package com.snm01234.springboot.web.dto;

import com.snm01234.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String fileName;
    private Integer replyCount;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String fileName) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.fileName = fileName;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .fileName(fileName)
                .replyCount(replyCount)
                .build();
    }
}
