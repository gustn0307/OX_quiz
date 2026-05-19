package com.my.ox_quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration // 환경 설정 클래스임을 알려줌, 스프링이 가장 먼저 살펴봄
public class SecurityConfig {
    // 비밀번호를 암호화 하는 기계를 Bean(인스턴스) 등록해서
    // 스프링이 관리하면서 필요한 곳에 꽂아주도록 한다.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
