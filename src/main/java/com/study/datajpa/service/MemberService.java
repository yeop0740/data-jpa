package com.study.datajpa.service;

import com.study.datajpa.entity.Member;
import com.study.datajpa.entity.Team;
import com.study.datajpa.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public Long save(String username, int age) {
        Member member = new Member(username, age);
        memberJpaRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findById(Long memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 멤버"));
        memberJpaRepository.delete(member);
    }

}
