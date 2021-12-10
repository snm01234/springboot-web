package com.snm01234.springboot.web;

import com.snm01234.springboot.config.auth.LoginUser;
import com.snm01234.springboot.config.auth.dto.SessionUser;
import com.snm01234.springboot.domain.posts.Posts;
import com.snm01234.springboot.external.UploadService;
import com.snm01234.springboot.service.PostsService;
import com.snm01234.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UploadService s3Service;


    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user,
                        @PageableDefault(size=5, sort = "id", direction = Sort.Direction.DESC)
                                Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
        //model.addAttribute("posts", postsService.findAllDesc());
        //model.addAttribute("posts", postsService.getPostsList(pageable));
        Page<Posts> posts = postsService.search(searchText, searchText, pageable);
        model.addAttribute("searchText", searchText);
        model.addAttribute("posts", posts);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber() + 1);
        model.addAttribute("next", pageable.next().getPageNumber() + 1);
        model.addAttribute("check", postsService.getListCheck(searchText, searchText, pageable)); // 마지막 페이지인지 체크용
        model.addAttribute("check2", postsService.getListCheck2(searchText, searchText, pageable)); // 첫번쨰 페이지인지 체크용
        ArrayList pageIndex = new ArrayList();
        for(int i = 1; i < posts.getTotalPages() + 1; i++) {
            pageIndex.add(i);
        }
        model.addAttribute("pageIndex", pageIndex);

        if(user != null) { // 세션에 저장된 값이 없으면 model에 값이 없어서 로그인 버튼이 보이게끔
            model.addAttribute("loginName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginName", user.getName());
        }


        return "posts-save";
    }
    @GetMapping("/posts/read/{id}")
    public String postsRead(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if(user != null) {
            model.addAttribute("loginName", user.getName());
        }
        if(dto.getFileName() != null) {
            model.addAttribute("fileUrl", s3Service.getFileUrl(dto.getFileName()));
        }
        return "posts-read";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("post", dto);
        model.addAttribute("loginName", user.getName());
        if(dto.getFileName() != null) {
            model.addAttribute("fileUrl", s3Service.getFileUrl(dto.getFileName()));
        }
        return "posts-update";
    }
}
