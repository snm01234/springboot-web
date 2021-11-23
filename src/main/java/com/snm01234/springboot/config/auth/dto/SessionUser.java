package com.snm01234.springboot.config.auth.dto;

import com.snm01234.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    //객체를 전송하기 위해 바이트로 변환(직렬화)하기위해, 성능 이슈 등 요인으로 User class와 별개의 dto 제작하는 것이 좋음.
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
