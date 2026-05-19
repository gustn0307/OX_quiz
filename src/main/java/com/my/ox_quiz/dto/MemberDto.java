package com.my.ox_quiz.dto;

import com.my.ox_quiz.entity.Member;
import com.my.ox_quiz.entity.MemberStatus;
import com.my.ox_quiz.entity.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long no; // PK, Auto Increment
    private String id; // 회원 ID
    private String password; // 회원 비번
    private RoleType role; // ADMIN or USER
    private MemberStatus status; // PENDING or APPROVED
    private Integer answerTrue = 0; // 맞은 수
    private Integer answerFalse = 0; // 틀린 수
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일

    // DTO -> Entity
    public static Member toEntity(MemberDto dto) {
        // Entity에 필요한 인자만 넣기 위해 빈 껍데기로 만든 후 필요한 값만 넣어준다.
        Member member = new Member();

        member.setId(dto.getId());
        member.setPassword(dto.getPassword());
        member.setRole(dto.getRole());
        member.setStatus(dto.getStatus());
        member.setAnswerTrue(dto.getAnswerTrue());
        member.setAnswerFalse(dto.getAnswerFalse());
        // PK no, 생성일과 수정일은 자동으로 처리되므로 Entity에 전달할 필요 없음
        return member;
    }

    // Entity -> DTO
    public static MemberDto toDto(Member member) {
        return new MemberDto(
           member.getNo(),
           member.getId(),
           member.getPassword(),
           member.getRole(),
           member.getStatus(),
           member.getAnswerTrue(),
           member.getAnswerFalse(),
           member.getCreatedAt(),
           member.getUpdatedAt()
        );
    }
}