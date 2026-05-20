package com.my.ox_quiz.service;

import com.my.ox_quiz.repository.MemberRepository;
import com.my.ox_quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final QuizRepository quizRepository;

}
