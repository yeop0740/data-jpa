package com.study.datajpa.controller;

import com.study.datajpa.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public SaveMemberResponse saveMember(@RequestBody SaveMemberRequest request) {
        Long id = memberService.save(request.getUsername(), request.getAge());
        return new SaveMemberResponse(id);
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
}
