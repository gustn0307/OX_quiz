package com.my.ox_quiz.repository;

import com.my.ox_quiz.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 특정 테스트할 곳의 테스트 코드로 진입하는 단축키: Ctrl + Shift + T
    // 또는 테스트할 클래스이름에 커서를 두고 Alt + Enter 후 create test 선택

    // PK가 아닌 일반 필드인데 id라는 이름을 쓰고 유니크 제약을 걸었다면,
    // 해당 필드명을 카멜케이스(Id)로 조합하여 메서드 이름을 정의합니다.
    // 이 방식은 Optional<Entity>나 Entity를 반환 타입으로 지정할 수 있습니다.
    // 사용법: findBy + 필드명 (첫 글자 대문자)
    Optional<Member> findById(String id);
}
