package com.my.ox_quiz.interceptor;

import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.entity.MemberStatus;
import com.my.ox_quiz.entity.RoleType;
import com.my.ox_quiz.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component // @Controller, @Service 등의 최상위 어노테이션
@RequiredArgsConstructor
public class HandlerInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
    private final MemberService memberService;

    // preHandle() 반환값
    // true : 요청을 계속 처리(Controller 실행 유지)
    // false : 요청 처리 중단(Controller 실행 안함)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(); // 컨트롤러로 전달되는 세션값 받음

        // 로그인 체크: 로그인 정보가 세션에 없는 경우 /my-page, /quiz/, /admin/ 접근시 로그인 화면으로 redirect
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        log.info("#### 인터셉터 디티오 : " + loginMember); // 로그로 확인
        if (ObjectUtils.isEmpty(loginMember)) {
            response.sendRedirect("/member/login"); // 로그인 화면으로 redirect
//            return false; // false: Controller 실행 중단
        }

        // 관리자 체크: ADMIN 아니면 퀴즈 CRUD관련, /admin/** URL 접근 시 접근 불가 처리
        if (request.getRequestURI().startsWith("/admin") && !loginMember.getRole().equals(RoleType.ADMIN))
            return false; // 접근 불가 처리

        // 승인 회원 체크: PENDING 상태이면 my-page로 redirect
        if(loginMember.getStatus().equals(MemberStatus.PENDING)){
            response.sendRedirect("/member/my-page"); // my-page로 redirect
        }
        return true; // true: Controller 실행 계속
    }

}
