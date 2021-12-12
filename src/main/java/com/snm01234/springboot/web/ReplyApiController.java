package com.snm01234.springboot.web;

import com.snm01234.springboot.domain.posts.Reply;
import com.snm01234.springboot.service.ReplyService;
import com.snm01234.springboot.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReplyApiController {
    private final ReplyService replyService;

    @GetMapping(value = "/api/v1/replies/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDto>> getListByPosts(@PathVariable("id") Long id) {
        return new ResponseEntity<>(replyService.getList(id), HttpStatus.OK);
    }

    @PostMapping("/api/v1/replies/posts")
    public Long replySave( @RequestBody ReplyDto replyDto) {
        return replyService.register(replyDto);
    }

    @DeleteMapping("/api/v1/replies/{rno}")
    public Long replyDelete(@PathVariable("rno") Long rno) {
        replyService.remove(rno);
        return rno;
    }
}
