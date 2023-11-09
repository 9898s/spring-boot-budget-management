package com.example.budget.global.config.dummy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.budget.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DummyMemberInitTest {

  @InjectMocks
  private DummyMemberInit dummyMemberInit;

  @Mock
  private MemberRepository memberRepository;

  @Test
  void 멤버_실행_횟수_test() throws Exception {
    // when
    dummyMemberInit.init(memberRepository).run();

    // then
    verify(memberRepository, times(3)).save(any());
  }
}