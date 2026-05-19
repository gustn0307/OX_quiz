package com.my.ox_quiz.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {
    PENDING("현재 승인 대기 상태입니다. 관리자가 승인하면 퀴즈 플레이가 가능합니다."),
    APPROVED("승인 완료 상태입니다. 퀴즈 플레이가 가능합니다.");

    private final String message;

    MemberStatus(String message) {
        this.message = message;
    }
}
