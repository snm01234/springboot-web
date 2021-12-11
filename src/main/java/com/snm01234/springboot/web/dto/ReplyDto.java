package com.snm01234.springboot.web.dto;

import com.snm01234.springboot.domain.posts.Posts;
import com.snm01234.springboot.domain.posts.Reply;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ReplyDto {
    private Long rno;
    private String text;
    private String replier;
    private Long id; //게시글 번호
//    private Posts posts;
    private LocalDateTime modifiedDate;

    public ReplyDto(Reply entity) {
        this.rno = entity.getRno();
        this.text = entity.getText();
        this.replier = entity.getReplier();
        this.modifiedDate = entity.getModifiedDate();
    }
    @Builder
    public ReplyDto(Long rno, String text, String replier, Long id, LocalDateTime modifiedDate) {
        this.rno = rno;
        this.text = text;
        this.replier = replier;
        this.id = id;
        this.modifiedDate = modifiedDate;
    }

    public Reply toEntity() {
        Posts posts = Posts.builder().id(id).build();
        return Reply.builder()
                .rno(rno)
                .text(text)
                .replier(replier)
                .posts(posts)
                .build();
    }
}
