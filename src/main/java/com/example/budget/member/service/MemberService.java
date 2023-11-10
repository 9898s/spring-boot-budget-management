package com.example.budget.member.service;

import com.example.budget.global.handler.exception.CustomApiException;
import com.example.budget.member.entity.Member;
import com.example.budget.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public Member getMemberEntity(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomApiException("찾을 수 없는 회원 번호입니다."));
  }
}
