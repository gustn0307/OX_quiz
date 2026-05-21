package com.my.ox_quiz.controller;

import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final MemberService memberService;

    @GetMapping("/members")
    public String showMembers(Model model, HttpSession session) {
        List<MemberDto> dtoList = memberService.findAll();
        log.info("어드민 컨트롤러/멤버리스트 : 전체 회원 리스트 dtoList : " + dtoList);

        model.addAttribute("dtoList", dtoList);
        model.addAttribute("member", new MemberDto());
        return "admin/member-list";
    }
    
    @PostMapping("/member/approve")
    public String memberApprove(MemberDto dto) {
        // 로그인은 관리자고 승인은 회원이므로 HTML에서 승인 버튼 누른 회원의 DTO 받아와야 함
        log.info("어드민컨트롤러/회원승인 : HTML에서 받아온 DTO : "+dto);

        memberService.updateStatus(dto.getNo());

        return "redirect:/admin/members";
    }
    
    @PostMapping("member/password")
    public String memberPwUpdate(MemberDto dto) {
        // 로그인은 관리자고 승인은 회원이므로 HTML에서 수정 버튼 회원의 DTO 받아와야 함
        log.info("어드민컨트롤러/비번수정 : HTML에서 받아온 DTO : "+dto);

        memberService.updatePassword(dto);
        return "redirect:/admin/members";
    }
}
