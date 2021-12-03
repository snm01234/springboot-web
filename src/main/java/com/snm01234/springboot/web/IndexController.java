package com.snm01234.springboot.web;

import com.snm01234.springboot.config.auth.LoginUser;
import com.snm01234.springboot.config.auth.dto.SessionUser;
import com.snm01234.springboot.domain.posts.Posts;
import com.snm01234.springboot.service.PostsService;
import com.snm01234.springboot.web.dto.FileDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        model.addAttribute("searchText", searchText);
        model.addAttribute("posts", posts);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postsService.getListCheck(pageable)); // 마지막 페이지인지 체크용
        model.addAttribute("check2", postsService.getListCheck2(pageable)); // 첫번쨰 페이지인지 체크용
        ArrayList pageIndex = new ArrayList();
        for(int i=0; i < posts.getTotalPages(); i++) {
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

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if(user != null) {
            model.addAttribute("loginName", user.getName());
        }
        return "posts-update";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile[] uploadfile, Model model)
            throws IllegalStateException, IOException {
        List<FileDto> list = new ArrayList<>();
        for(MultipartFile file: uploadfile) {
            if(!file.isEmpty()) {
                String savePath = System.getProperty("user.dir") + "\\files";
                /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
                if (!new File(savePath).exists()) {
                    try{
                        new File(savePath).mkdir();
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }
                }

                // UUID를 이용해 unique한 파일 이름 만들어줌
                FileDto dto = new FileDto(UUID.randomUUID().toString(),
                        file.getOriginalFilename(),
                        file.getContentType());
                list.add(dto);

                File newFileName = new File(dto.getUuid() + "_" + dto.getFileName());
                //전달된 내용을 실제 물리적인 파일로 저장해준다.
                String filePath = savePath + "\\" + newFileName;
                file.transferTo(Paths.get(filePath));
            }
        }
        model.addAttribute("files", list);
        return "redirect:";
    }


}
