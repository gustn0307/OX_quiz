package com.my.ox_quiz.service;

import com.my.ox_quiz.dto.MemberDto;
import com.my.ox_quiz.entity.Member;
import com.my.ox_quiz.entity.MemberStatus;
import com.my.ox_quiz.entity.RoleType;
import com.my.ox_quiz.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    public void signUp(MemberDto dto) {
        Member member = new Member(); // 빈 Entity 생성

        // DTO -> Entity 변환(비밀번호 인코딩, role USER로 설정)
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRole(RoleType.USER);
        dto.setStatus(MemberStatus.PENDING); // 회원가입 시 권한을 PENDING으로 설정
        member = MemberDto.toEntity(dto);

        memberRepository.save(member); // DB에 저장
    }

    // 로그인
    // 로그인 성공하면 세션 열기
    public MemberDto login(MemberDto dto) {
        MemberDto loginDto = findByID(dto.getId());

        if (loginDto == null) // DB에서 찾아 오지 못 했으면 null 리턴
            return null;

        // 비밀번호를 암호 디코딩(Decoding) 후 비교
        // matches(html에서 받아온 비밀번호, DB에 저장되어있는 암호화된 비밀번호)
        // 비밀번호 틀리면 null 리턴
        boolean matches = passwordEncoder.matches(dto.getPassword(), loginDto.getPassword());
        if (!matches)
            return null;

        // DB에서 찾아온 후 입력한 평문 비밀번호와 DB에서 가져온 비밀번호를 Decoding한 값이 일치하면 loginDto 리턴
        return loginDto;
    }

    // unique인 id로 DB에서 엔티티 찾아서 DTO로 변환해서 반환
    private MemberDto findByID(String id) {
        Member member = memberRepository.findById(id).orElse(null);

        if (member == null) // 찾지 못하면 null 리턴
            return null;

        return MemberDto.toDto(member); // 찾은 Entity를 DTO로 변환해서 리턴
    }

//    // PK인 no로 DB에서 엔티티 찾아서 DTO로 변환해서 반환
//    private MemberDto findByNo(Long no) {
//        Member member = memberRepository.findById(no).orElse(null);
//
//        if (member == null) // 찾지 못하면 null 리턴
//            return null;
//
//        return MemberDto.toDto(member); // 찾은 Entity를 DTO로 변환해서 리턴
//    }
}
