package com.my.ox_quiz.config;

import com.my.ox_quiz.interceptor.MyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final MyInterceptor myInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        // 어느 URL 요청 시 interceptor가 가로챌 것인지 설정
//        registry.addInterceptor(myInterceptor)
//                .addPathPatterns("/my-page",
//                        "/quiz/**",
//                        "/admin/**",
//                        "/");
//    }
}
