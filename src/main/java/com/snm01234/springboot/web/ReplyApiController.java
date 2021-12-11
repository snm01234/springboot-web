package com.snm01234.springboot.web;

import com.snm01234.springboot.service.ReplyService;
import com.snm01234.springboot.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReplyApiController {
    private final ReplyService replyService;

    @GetMapping(value = "/api/v1/replies/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDto>> getListByPosts(@PathVariable("id") Long id) {
        return new ResponseEntity<>(replyService.getList(id), HttpStatus.OK);
    }

}
