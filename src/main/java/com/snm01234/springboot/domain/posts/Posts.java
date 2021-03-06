package com.snm01234.springboot.domain.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.snm01234.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment에 필요
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Column(nullable = false)
    private String fileName;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> reply;

    //댓글개수
    @Formula("(SELECT count(1) FROM reply r WHERE r.posts_id = id)")
    private Integer replyCount;

    @Builder
    public Posts(Long id, String title, String content, String author, String fileName, Integer replyCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.fileName = fileName;
        this.replyCount = replyCount;
    }

    public void update(String title, String content, String fileName) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }
    public void updateFile(String fileName) {
        this.fileName = fileName;
    }
}
