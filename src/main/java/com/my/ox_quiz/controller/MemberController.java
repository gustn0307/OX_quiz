package com.my.ox_quiz.controller;

import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String signUpView(Model model) {
        model.addAttribute("dto", new MemberDto()); // 빈 DTO를 보낸다

        return "member/join";
    }

    @PostMapping("/join")
    public String signUp(@ModelAttribute("dto") MemberDto dto) {
        log.info("@@@@ MemberDto: " + dto); // 로그로 DTO 확인

        // 서비스에서 DB에 회원 정보 저장
        memberService.signUp(dto);
        return "redirect:/member/login"; // 회원가입 후 로그인 화면으로 이동
    }

    @GetMapping("/login")
    public String loginView(Model model) {
        model.addAttribute("dto", new MemberDto()); // 빈 DTO 보내기
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("dto") MemberDto dto,
                        HttpSession session) {

        log.info("로그인 DTO : " + dto);

        MemberDto loginDto = memberService.login(dto);

        if (ObjectUtils.isEmpty(loginDto))  // 로그인 실패하면 계속 로그인 페이지로
            return "redirect:/member/login";

        // 로그인 성공하면 세션에 DTO 전체를 담기
        session.setAttribute("loginDto", loginDto);

        // 세션 유지 시간 설정
        // 인자로 준 숫자는 초 단위
        // 세션 유지 시간이 지나면 session에 있던 정보 비운다.
        session.setMaxInactiveInterval(60 * 30); // 60초 * 30 = 30분

        return "member/my-page"; // 로그인 성공하면 my-page로 이동
    }

    // 로그아웃(세션 제거)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체 삭제

        // 초기화면으로 이동
        return "redirect:/";
    }
}
