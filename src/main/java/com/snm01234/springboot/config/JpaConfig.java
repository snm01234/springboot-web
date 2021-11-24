package com.snm01234.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화. 테스트코드를 위해서 Application.java에서 분리
public class JpaConfig {
}
