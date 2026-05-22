package com.my.ox_quiz.dto;

import com.my.ox_quiz.entity.Quiz;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long id; // PK, Auto Increment
    private String content; // 퀴즈 내용, 필수, DB에서 text타입으로
    private Boolean answer; // 정답 여부, 필수
    private String writer; // 작성자
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일

    // DTO -> Entity
    public static Quiz toEntity(QuizDto dto) {
        Quiz quiz = new Quiz();

        quiz.setContent(dto.getContent());
        quiz.setAnswer(dto.getAnswer());
        quiz.setWriter(dto.getWriter());
        // PK id, 생성일과 수정일은 자동으로 처리되므로 Entity에 전달할 필요 없음
        return quiz;
    }


    // Entity -> DTO
    public static QuizDto toDto(Quiz quiz) {
        return new QuizDto(
           quiz.getId(),
           quiz.getContent(),
           quiz.getAnswer(),
           quiz.getWriter(),
           quiz.getCreatedAt(),
           quiz.getUpdatedAt()
        );
    }
}
