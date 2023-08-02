package com.study.datajpa.controller;

import com.study.datajpa.entity.Member;
import com.study.datajpa.entity.Team;
import com.study.datajpa.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public SaveMemberResponse saveMember(@RequestBody SaveMemberRequest request) {
        Long id = memberService.save(request.getUsername(), request.getAge());
        return new SaveMemberResponse(id);
    }

    @GetMapping("/api/v1/member/{memberId}")
    public FindMemberResponse findMember(@PathVariable Long memberId) throws RuntimeException {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당하는 멤버는 존재하지 않습니다."));
        return new FindMemberResponse(member);
    }

    @GetMapping("api/v1/members")
    public MembersResponse<List<MemberService.MemberDto>> findMembers() {
        return new MembersResponse<>(memberService.findAll());
    }

    @DeleteMapping("/api/v1/member/{memberId}")
    public DeleteMemberResponse delete(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return new DeleteMemberResponse(memberId);
    }

    @Data
    @AllArgsConstructor
    private static class SaveMemberResponse {

        private Long memberId;

    }

    @Data
    private static class SaveMemberRequest {

        private String username;

        private int age;

    }

    @Data
    @AllArgsConstructor
    private static class FindMemberResponse {

        private Long memberId;

        private String username;

        private int age;

        private Team team;

        public FindMemberResponse(Member member) {
            this.memberId = member.getId();
            this.username = member.getUsername();
            this.age = member.getAge();
            this.team = member.getTeam();
        }

    }

    @Data
    @AllArgsConstructor
    private static class DeleteMemberResponse {

        private Long memberId;

    }

    @Data
    @AllArgsConstructor
    private static class MembersResponse<T> {

        T data;

    }
}
