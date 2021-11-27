package com.snm01234.springboot.service;

import com.snm01234.springboot.domain.posts.Posts;
import com.snm01234.springboot.domain.posts.PostsRepository;
import com.snm01234.springboot.web.dto.PostsListResponseDto;
import com.snm01234.springboot.web.dto.PostsResponseDto;
import com.snm01234.springboot.web.dto.PostsSaveRequestDto;
import com.snm01234.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional(readOnly = true) // 트랜잭션 범위 유지하면서 조회기능만 남겨두어 조회속도 개선
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // == map.(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }

    @Transactional
    public Page<Posts> getPostsList(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }

    @Transactional
    public Page<Posts> search(String title, String content, Pageable pageable) {
        Page<Posts> postsList = postsRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        return postsList;
    }

    @Transactional
    public Boolean getListCheck(Pageable pageable) {
        Page<Posts> saved = getPostsList(pageable);
        Boolean check = saved.hasNext(); // next가 있으면 true

        return check;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        return new PostsResponseDto(entity);
    }


}
