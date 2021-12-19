package com.snm01234.springboot.config.auth;

import com.snm01234.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    // h2-console 화면 사용하기 위해 disable
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile", "/posts/read/**",
                            "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/webjars/**", "/swagger-ui.html" ).permitAll()
                    .antMatchers("/api/v1/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .anyRequest().authenticated()   //나머지 url 인증되어야한다.
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  // 로그아웃 성공시 /로 이동
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
