package com.snm01234.springboot.service;

import com.snm01234.springboot.domain.posts.Posts;
import com.snm01234.springboot.domain.posts.Reply;
import com.snm01234.springboot.domain.posts.ReplyRepository;
import com.snm01234.springboot.web.dto.PostsListResponseDto;
import com.snm01234.springboot.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Long register(ReplyDto replyDto) { //댓글 등록
        return replyRepository.save(replyDto.toEntity()).getRno();
    }

    @Transactional
    public List<ReplyDto> getList(Long id) { //게시물의 댓글 목록 조회
        List<Reply> result = replyRepository.getRepliesByPostsOrderByRno(Posts.builder().id(id).build());
        return result.stream()
                .map(ReplyDto::new) // == map.(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void modify(ReplyDto replyDto) { //댓글 수정
        Reply reply = replyDto.toEntity();
        replyRepository.save(reply);
    }

    @Transactional
    public void remove(Long rno) { //댓글 삭제
        Reply reply = replyRepository.findById(rno).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. rno=" + rno));
        replyRepository.delete(reply);
    }

}
