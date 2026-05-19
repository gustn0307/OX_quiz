package com.my.ox_quiz.interceptor;

import com.my.ox_quiz.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // @Controller, @Service 등의 최상위 어노테이션
@RequiredArgsConstructor
public class MyInterceptor implements HandlerInterceptor {
    private final MemberService memberService;

    // preHandle() 반환값
    // true : 요청을 계속 처리(Controller 실행 유지)
    // false : 요청 처리 중단(Controller 실행 안함)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(); // 컨트롤러로 전달되는 세션값 받음
        Object loginMember = session.getAttribute("loginMember");
        // 서비스를 통해 DB에서 해당 멤버의 정보 받아오기

        // 로그인 안 된 경우 -> 로그인 페이지로 redirect
        if (ObjectUtils.isEmpty(loginMember)) {
            response.sendRedirect("/member/login");
            return false; // false: Controller 실행 중단
        }

        // ADMIN 아닌 경우 로그인 접근 불가

        // PENDING 상태인 경우 마이페이지로 redirect

        return true; // true: Controller 실행 계속
    }
}
