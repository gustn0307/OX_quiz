package com.my.ox_quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Quiz extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK, Auto Increment

    @Column(nullable = false, columnDefinition = "text")
    private String content; // 퀴즈 내용, 필수, DB에서 text타입으로

    @Column(nullable = false)
    private Boolean answer; // 정답 여부, 필수

    @Column(nullable = false)
    private String writer;
}
