package com.my.ox_quiz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate // 변경된 필드만 실제 UPDATE 쿼리에 반영
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; // PK, Auto Increment

    @Column(nullable = false, unique = true)
    private String id; // 회원 ID

    @Column(nullable = false)
    private String password; // 회원 비번

    @Enumerated(EnumType.STRING)
    private RoleType role; // ADMIN or USER

    @Enumerated(EnumType.STRING)
    private MemberStatus status; // PENDING or APPROVED

    @Column(columnDefinition = "integer default 0") // default 0으로 설정
    private Integer answerTrue = 0; // 맞은 수

    @Column(columnDefinition = "integer default 0")
    private Integer answerFalse = 0; // 틀린 수
}