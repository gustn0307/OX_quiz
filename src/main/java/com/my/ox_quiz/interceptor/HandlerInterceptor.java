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
        // request.getSession(false) : "이미 존재하는 세션이 있다면 해당 세션을 가져오고,
        // 없다면 새로 생성하지 않고 null을 반환하라"는 의미
        HttpSession session = request.getSession(false); // 컨트롤러로 전달되는 세션값 받음

        String requestURI = request.getRequestURI(); // 사용자가 요청했던 URI
        log.info("인터셉터 : 사용자가 요청했던 URI : " + requestURI);

        // 로그인 체크: 로그인 정보가 세션에 없는 경우 /my-page, /quiz/, /admin/ 접근시 로그인 화면으로 redirect
        MemberDto loginMember = (MemberDto) session.getAttribute("loginDto");
        log.info("인터셉터 : 세션에서 받아온 DTO : " + loginMember); // 로그로 확인
        if (ObjectUtils.isEmpty(loginMember)) {
            response.sendRedirect("/member/login"); // 로그인 화면으로 redirect
        }

        // 관리자 체크: ADMIN 아니면 /quiz/** 관련, /admin/** URL 접근 시 접근 불가 처리
        // /quiz/play, /quiz/check는 접근 가능함
        if (( !requestURI.equals("/quiz/check") && !requestURI.equals("/quiz/play")) && !loginMember.getRole().equals(RoleType.ADMIN))
            response.sendRedirect(requestURI); // 접근 불가 처리(요청했던 URL로 다시 이동시킴)

        // 승인 회원 체크: PENDING 상태이면 my-page로 redirect
        if(loginMember.getStatus().equals(MemberStatus.PENDING)){
            response.sendRedirect("/member/my-page"); // my-page로 redirect
        }
        return true; // true: Controller 실행 계속
    }

}
