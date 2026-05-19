package com.my.ox_quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate // 생성일 자동으로 지정
    @Column(updatable = false) // 생성일은 수정 불가하게 하기
    private LocalDateTime createdAt; // 생성일

    @LastModifiedDate // 마지막 수정일 자동으로 지정
    private LocalDateTime updatedAt; // 수정일

}
