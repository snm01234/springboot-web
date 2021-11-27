package com.snm01234.springboot.web;

import com.snm01234.springboot.config.auth.LoginUser;
import com.snm01234.springboot.config.auth.dto.SessionUser;
import com.snm01234.springboot.domain.posts.Posts;
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

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user,
                        @PageableDefault(size=2, sort = "id", direction = Sort.Direction.DESC)
                                Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
        //model.addAttribute("posts", postsService.findAllDesc());
        //model.addAttribute("posts", postsService.getPostsList(pageable));
        Page<Posts> posts = postsService.search(searchText, searchText, pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postsService.getListCheck(pageable));
        if(user != null) { // 세션에 저장된 값이 없으면 model에 값이 없어서 로그인 버튼이 보이게끔
            model.addAttribute("loginName", user.getName());
        }
        return "index";
    }

    /*@GetMapping("/posts/search")
    public String search(String title, String content,
                         @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Posts> searchList = postsService.search(title, content, pageable);
        model.addAttribute("searchList", searchList);
        return "posts-search";
    }*/


    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginName", user.getName());
        }
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if(user != null) {
            model.addAttribute("loginName", user.getName());
        }
        return "posts-update";
    }


}
