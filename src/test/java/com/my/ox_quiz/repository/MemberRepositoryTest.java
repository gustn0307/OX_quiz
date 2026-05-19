package com.my.ox_quiz.repository;

import com.my.ox_quiz.entity.Member;
import com.my.ox_quiz.entity.MemberStatus;
import com.my.ox_quiz.entity.RoleType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

// @Transactional : DB 조작 과정을 Transaction으로 시작하여 종료 후 roll back한다.
//                           클래스에 붙이면 아래의 모든 메서드에 적용, 메서드에 붙이면 해당 메서드만 적용
@Transactional
@SpringBootTest // 통합 테스트
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    // ADMIN 생성하고 확인, 비밀번호 변경 확인
    @Test
    @DisplayName("관리자 만들기")
    void createAdmin() {
        Member member = new Member(); // 빈 멤버 만들기
        member.setId("admin");
        member.setPassword("1111");
        member.setRole(RoleType.ADMIN);
        member.setStatus(MemberStatus.APPROVED);

        memberRepository.save(member);
        System.out.println("관리자 생성 완료!!!");
    }

}