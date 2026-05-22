package com.my.ox_quiz.controller;

import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.dto.QuizDto;
import com.my.ox_quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Slf4j
public class QuizController {
    private final QuizService quizService;

    // 퀴즈 관리 화면
    @GetMapping("")
    public String quizManagement(Model model, HttpSession session) {
        MemberDto dto = (MemberDto) session.getAttribute("loginDto");
        log.info("퀴즈 컨트롤러 : DTO : " + dto);

        // 이상한 접근(URL 직접 쳐서 접근 등)을 통해 요청한 사용자 -> 로그인 화면으로 이동
        if (ObjectUtils.isEmpty(dto))
            return "redirect:/member/login";

        List<QuizDto> quizDtoList = quizService.findAll();
        log.info("퀴즈 컨트롤러/퀴즈관리 퀴즈 전체 리스트 : " + quizDtoList);

        // DTO를 모델에 담아 뷰에 전달
        model.addAttribute("dto", dto);
        model.addAttribute("quizDto", new QuizDto());
        model.addAttribute("quizDtoList", quizDtoList);
        return "quiz/list";
    }

    // 퀴즈 등록
    @PostMapping("/insert")
    public String quizInsert(@ModelAttribute("quizDto") QuizDto dto,
                             Model model) {
        log.info("퀴즈 컨트롤러/퀴즈 등록 : dto : " + dto);
        quizService.save(dto);

        return "redirect:/quiz";
    }

    // 퀴즈 수정 화면
    @GetMapping("/{id}")
    public String quizUpdateView(@PathVariable Long id,
                                 Model model) {
        log.info("퀴즈 컨트롤러/퀴즈업데이트화면 : 전달할 id : " + id); // 퀴즈 관리 화면에서 수정 버튼 누른 퀴즈의 id

        QuizDto updateDto = quizService.findById(id);
        model.addAttribute("dto", updateDto); // 수정 버튼 누른 퀴즈의 id를 가지는 DTO

        return "quiz/update";
    }

    // 퀴즈 수정
    @PostMapping("/update")
    public String quizUpdate(QuizDto quizDto) {
        log.info("퀴즈 컨트롤러/퀴즈 수정 : HTML에서 넘어온 DTO : " + quizDto); // 여기 확인 필요

        quizService.update(quizDto);
        return "redirect:/quiz";
    }

    // 퀴즈 삭제
    @PostMapping("/delete")
    public String quizDelete(QuizDto quizDto) {
        log.info("퀴즈 컨트롤러/퀴즈 삭제 : HTML에서 넘어온 DTO : " + quizDto); // id만 가진 DTO 받아오기

        quizService.delete(quizDto.getId()); // 삭제
        return "redirect:/quiz";
    }

    // 랜덤 퀴즈 1개 출력
    @GetMapping("/play")
    public String quizPlay(Model model) {
        List<QuizDto> quizDtoList = new ArrayList<>(); // 전체 퀴즈 리스트
        quizDtoList = quizService.findAll();

        log.info("퀴즈 컨트롤러/퀴즈풀기 : 전체 리스트 : " + quizDtoList);
        int randomIndex = (int)(Math.random()*quizDtoList.size()); // 0 ~ 리스트 사이즈 까지의 랜덤 인덱스 정수
        log.info("퀴즈 컨트롤러/퀴즈풀기 : 리스트의 랜덤 인덱스 : " + randomIndex);
        log.info("퀴즈 컨트롤러/퀴즈풀기 : 랜덤 DTO : " + quizDtoList.get(randomIndex));

        model.addAttribute("dtoList", quizDtoList); // 리스트가 비었는지 확인용으로 HTML에 보내기
        model.addAttribute("randomQuiz", quizDtoList.get(randomIndex)); // 섞인 리스트의 첫 번째 값 보내기
        model.addAttribute("resultDto", new QuizDto());

        return "quiz/play";
    }

    @PostMapping("/check")
    public String quizCheck(@ModelAttribute("resultDto") QuizDto quizDto,
                            Model model) {

//        MemberDto loginDto = (MemberDto) session.getAttribute("loginDto"); // ADMIN 인지 확인용
        QuizDto forCheckDto = quizService.findById(quizDto.getId()); // 정답 맞췄는지 확인(findById로 찾아서 비교하기)
        boolean result = forCheckDto.getAnswer().equals(quizDto.getAnswer()); // 정답 일치하는지 저장하는 result 변수

        log.info("퀴즈 컨트롤러/퀴즈 정답 제출 : HTML에서 넘어온 DTO : " + quizDto); // id와 answer만 가져오기
        log.info("퀴즈 컨트롤러/퀴즈 정답 제출 : 정답확인용 DTO : " + forCheckDto); // 정답 확인용 DTO
        log.info("퀴즈 컨트롤러/퀴즈 정답 제출 : 정답 일치 여부 변수 : " + result); // 정답 일치 여부 변수

        model.addAttribute("result", result); // 정답 맞췄는지 result.html에 전달

        return "quiz/result";
    }
}
