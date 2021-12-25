package com.snm01234.springboot.web;

import com.snm01234.springboot.config.auth.LoginUser;
import com.snm01234.springboot.config.auth.dto.SessionUser;
import com.snm01234.springboot.service.PostsService;
import com.snm01234.springboot.web.dto.PostsFileUpdateRequestDto;
import com.snm01234.springboot.web.dto.PostsResponseDto;
import com.snm01234.springboot.web.dto.PostsSaveRequestDto;
import com.snm01234.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody @Valid PostsSaveRequestDto requestDto) {
        return  postsService.save(requestDto);
    }

    //@PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or #requestDto.author == #user.name)")
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody @Valid PostsSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return postsService.update(id, requestDto);
    }
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }

    @PutMapping("/api/v1/posts/file/{id}")
    public Long updateFile(@PathVariable Long id, @RequestBody @Valid PostsFileUpdateRequestDto requestDto) {
        return postsService.updateFile(id, requestDto);
    }

}
