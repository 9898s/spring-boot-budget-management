package com.example.budget.global.config.dummy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.budget.budget.repository.BudgetRepository;
import com.example.budget.category.repository.CategoryRepository;
import com.example.budget.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DummyDataInitTest {

  @InjectMocks
  private DummyDataInit dummyCategoryInit;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private BudgetRepository budgetRepository;

  @Test
  void 초기_데이터_실행_횟수_test() throws Exception {
    // when
    dummyCategoryInit.init(categoryRepository, memberRepository, budgetRepository).run();

    // then
    verify(categoryRepository, times(3)).save(any());
    verify(memberRepository, times(10)).save(any());
    verify(budgetRepository, times(10)).save(any());
  }
}