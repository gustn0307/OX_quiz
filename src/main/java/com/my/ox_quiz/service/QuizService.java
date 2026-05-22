package com.my.ox_quiz.service;

import com.my.ox_quiz.dto.QuizDto;
import com.my.ox_quiz.entity.Quiz;
import com.my.ox_quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {
    private final QuizRepository quizRepository;

    public void save(QuizDto dto) {
        log.info("퀴즈 서비스/퀴즈 등록 : 컨트롤러에서 받아온 DTO : " + dto);

        quizRepository.save(QuizDto.toEntity(dto)); // 엔티티로 변환해서 저장
    }

    // 전체 퀴즈 목록 가져오기
    public List<QuizDto> findAll() {
        return quizRepository.findAll().stream().map(QuizDto::toDto).toList();
    }

    public void delete(Long id) {
        log.info("퀴즈 서비스/퀴즈 삭제 : 컨트롤러에서 받아온 ID : " + id);
        quizRepository.deleteById(id);
    }

    public QuizDto findById(Long id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) // id로 퀴즈 못 찾으면 null 리턴
            return null;

        return QuizDto.toDto(quiz);
    }

    public void update(QuizDto quizDto) {
        log.info("퀴즈 서비스/퀴즈 수정 : 컨트롤러에서 받아온 DTO : " + quizDto);

        // 컨트롤러에서 받아온 DTO의 ID로 DB에서 찾아온 DTO를 Entity로 변환
        // 이미 목록이 있는 상태에서 선택한 DTO가 넘어오기 때문에 findById로 찾았을 때 없을 수 없다.
        // findById()로 찾아와서 quiz에 넣어줘야 Spring이 UPDATE문인 것을 구분한다.
        // new로 빈 껍데기 만들고 dto로 넣으면 Spring이 UPDATE인지 구분 못해서 INSERT문만 실행됨
        Quiz quiz = quizRepository.findById(quizDto.getId()).orElse(null);

        if(!ObjectUtils.isEmpty(quiz)){ // quiz 찾았으면 수정하고 저장
            quiz.setContent(quizDto.getContent());
            quiz.setAnswer(quizDto.getAnswer());
            quiz.setWriter(quizDto.getWriter());
            quizRepository.save(quiz);
        }

    }
}
