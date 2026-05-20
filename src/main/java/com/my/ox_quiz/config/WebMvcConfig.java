package com.my.ox_quiz.config;

import com.my.ox_quiz.interceptor.HandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final HandlerInterceptor handlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 어느 URL 요청 시 interceptor가 가로챌 것인지 설정
        registry.addInterceptor(handlerInterceptor)
                .addPathPatterns(
                        "/my-page",
                        "/quiz/**",
                        "/admin/**"
                )
                .excludePathPatterns(
                        "/",
//                        "/member/login",
                        "/member/logout",
                        "/css/**",
                        "js/**"
                );
    }
}
