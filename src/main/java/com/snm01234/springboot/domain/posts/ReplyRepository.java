package com.snm01234.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //Posts 삭제 시 댓글 같이 삭제
    @Modifying
    @Query("delete from Reply r where r.posts.id =:id")
    void deleteById(Long id);

    //게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByPostsOrderByRno(Posts posts);
}
