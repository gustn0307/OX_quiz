package com.my.ox_quiz.controller;

import com.my.ox_quiz.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Slf4j
public class QuizController {
    @GetMapping("")
    public String quizManagement(Model model, HttpSession session) {
        MemberDto dto = (MemberDto) session.getAttribute("loginDto");
        log.info("퀴즈 컨트롤러 DTO : " + dto);

        // 이상한 접근(URL 직접 쳐서 접근 등)을 통해 요청한 사용자 -> 로그인 화면으로 이동
        if (ObjectUtils.isEmpty(dto))
            return "redirect:/member/login";

        // DTO를 모델에 담아 뷰에 전달
        model.addAttribute("dto", dto);
        return "quiz/list";
    }
}
