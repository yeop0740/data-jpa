package com.study.datajpa.service;

import com.study.datajpa.entity.Member;
import com.study.datajpa.entity.Team;
import com.study.datajpa.repository.MemberJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<MemberDto> findAll() {
        return memberJpaRepository.findAll().stream()
                .map(MemberDto::new)
                .toList();
    }

    public long count() {
        return memberJpaRepository.count();
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 멤버"));
        memberJpaRepository.delete(member);
    }

    @Data
    @AllArgsConstructor
    public static class MemberDto {

        private Long memberId;

        private String username;

        private int age;

        private Team team;

        public MemberDto(Member member) {
            this.memberId = member.getId();
            this.username = member.getUsername();
            this.age = member.getAge();
            this.team = member.getTeam();
        }
    }
}
