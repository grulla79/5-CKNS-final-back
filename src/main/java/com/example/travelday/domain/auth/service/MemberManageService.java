package com.example.travelday.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.travelday.domain.auth.dto.response.MemberInfoResDto;
import com.example.travelday.domain.auth.entity.Member;
import com.example.travelday.domain.auth.repository.MemberRepository;
import com.example.travelday.global.exception.CustomException;
import com.example.travelday.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberManageService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberInfoResDto getInfo(String userId) {

        Member member =
                memberRepository
                        .findByUserId(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoResDto.builder().nickname(member.getNickname()).build();
    }

    @Transactional(readOnly = true)
    public boolean checkDuplicateNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional
    public void updateNickname(String userId, String nickname) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateNickname(nickname);
    }
}
