package com.my.ox_quiz.controller;

import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.entity.RoleType;
import com.my.ox_quiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    // 회원가입 화면
    @GetMapping("/join")
    public String signUpView(Model model) {
        model.addAttribute("dto", new MemberDto()); // 빈 DTO를 보낸다

        return "member/join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String signUp(@ModelAttribute("dto") MemberDto dto,
                         RedirectAttributes redirectAttributes) {
        log.info("@@@@ MemberDto: " + dto); // 로그로 DTO 확인

        // 서비스에서 DB에 회원 정보 저장
        boolean isDuplicated = memberService.signUp(dto);
        if(!isDuplicated) {
            redirectAttributes.addFlashAttribute("message", "중복된 회원 아이디입니다. 다른 아이디를 입력해주세요.");
            return "redirect:/member/join";
        }
        return "redirect:/member/login"; // 회원가입 후 로그인 화면으로 이동
    }

    // 로그인 화면
    @GetMapping("/login")
    public String loginView(HttpSession session,
                            Model model) {

        // "error" 세션에 저장된 에러가 있는지 확인(로그인 실패했는지 확인)
        String error = (String) session.getAttribute("error");

        if (error != null) { // 로그인 실패시 실패 경고창 출력을 위해 model에 error 값 담아서 보내기
            model.addAttribute("error", error); //
            session.removeAttribute("error");  // 세션 에러 1회성으로 삭제
        }

        model.addAttribute("dto", new MemberDto()); // 빈 DTO 보내기

        return "member/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute("dto") MemberDto dto,
                        HttpSession session) {

        log.info("컨트롤러/로그인 : HTML에서 넘어온 로그인 DTO : " + dto);

        MemberDto loginDto = memberService.login(dto);

        if (ObjectUtils.isEmpty(loginDto)) { // 로그인 실패하면 계속 로그인 페이지로
            session.setAttribute("error", "true"); // "error" 세션에 true 담기
            return "redirect:/member/login";
        }
        // 로그인 성공하면 세션에 DTO 전체를 담기
        session.setAttribute("loginDto", loginDto);
        log.info("컨트롤러/로그인 : HTML에서 넘어온 로그인 정보로 DB에서 찾아온 DTO : " + loginDto);

        // 세션 유지 시간 설정
        // 인자로 준 숫자는 초 단위
        // 세션 유지 시간이 지나면 session에 있던 정보 비운다.
        session.setMaxInactiveInterval(60 * 30); // 60초 * 30 = 30분

        if (loginDto.getRole() == RoleType.USER) // role이 USER면 my-page로 이동
            return "redirect:/member/my-page";
        else  // role이 ADMIN이면 /quiz URL로 이동(퀴즈 관리 화면)
            return "redirect:/quiz";
    }

    // 로그아웃(세션 제거)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체 삭제

        // 초기화면으로 이동
        return "redirect:/";
    }

    // 마이 페이지 화면
    @GetMapping("/my-page")
    public String myPageView(HttpSession session, Model model) {
        MemberDto loginDto = (MemberDto) session.getAttribute("loginDto");
        log.info("컨트롤러/마이페이지 :  loginDto : " + loginDto); // 로그로 제대로 DTO 가져오는지 확인

        // 이상한 접근(URL 직접 쳐서 접근 등)을 통해 요청한 사용자 -> 로그인 화면으로 이동
        if (ObjectUtils.isEmpty(loginDto))
            return "redirect:/member/login";

        // DTO를 모델에 담아 뷰에 전달
        model.addAttribute("dto", loginDto);

        return "member/my-page";
    }

    // 내 비밀번호 수정
    @PostMapping("/password")
    public String updatePassword(@ModelAttribute("dto") MemberDto dto,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        MemberDto updateDto = (MemberDto) session.getAttribute("loginDto");

        log.info("컨트롤러/비번수정 : 비밀번호 수정 전 DTO : " + updateDto); // 수정해야 할 원본 DTO
        log.info("컨트롤러/비번수정 :  비밀번호 수정 HTML에서 받아온 DTO : " + dto); // my-page에서 받아온 수정할 비밀번호 가진 DTO

        updateDto.setPassword(dto.getPassword()); // 비밀번호만 바꾸기
        log.info("컨트롤러/비번수정 :  비밀번호 수정 후 DTO : " + updateDto); // 수정해야 할 원본 DTO

        memberService.updatePassword(updateDto);

        // 다시 로그인하라고 메시지 보내기
        redirectAttributes.addFlashAttribute("message", "수정한 비밀번호로 다시 로그인해주세요.");

        return "redirect:/member/my-page";  // 비밀번호 수정 후 다시 my-page로 redirect
    }

}
