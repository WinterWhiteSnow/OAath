package com.example.demo.model.service;

import com.example.demo.model.dto.entity.Member;
import com.example.demo.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 이메일로 멤버 조회
    public Optional<Member> getMember(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    // 멤버 저장
    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}
