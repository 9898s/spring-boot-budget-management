package com.example.budget.budget.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.budget.budget.dto.BudgetRequestDto.BudgetSetRequestDto;
import com.example.budget.budget.dto.BudgetResponseDto.BudgetSetResponseDto;
import com.example.budget.budget.entity.Budget;
import com.example.budget.budget.repository.BudgetRepository;
import com.example.budget.category.entity.Category;
import com.example.budget.category.service.CategoryService;
import com.example.budget.member.entity.Member;
import com.example.budget.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

  @InjectMocks
  private BudgetService budgetService;

  @Mock
  private BudgetRepository budgetRepository;

  @Mock
  private MemberService memberService;

  @Mock
  private CategoryService categoryService;

  @Test
  void 예산설정_test() throws Exception {
    // given
    Member hong = Member.builder()
        .id(1L)
        .account("hong")
        .password("1234")
        .build();

    Category home = Category.builder()
        .id(1L)
        .name("주거")
        .build();

    Budget budget = Budget.builder()
        .id(1L)
        .amount(1000L)
        .member(hong)
        .category(home)
        .build();

    BudgetSetRequestDto requestDto = new BudgetSetRequestDto();
    requestDto.setAmount(1000L);
    requestDto.setMemberId(1L);
    requestDto.setCategoryId(1L);

    // stub 1
    when(memberService.getMemberEntity(anyLong())).thenReturn(hong);

    // stub 2
    when(categoryService.getCategoryId(anyLong())).thenReturn(home);

    // stub 3
    when(budgetRepository.existsByMemberAndCategory(any(), any())).thenReturn(false);

    // stub 4
    when(budgetRepository.save(any())).thenReturn(budget);

    // when
    BudgetSetResponseDto responseDto = budgetService.setBudget(requestDto);

    // then
    assertEquals(1000L, responseDto.getAmount());
    assertEquals(1L, responseDto.getMemberId());
    assertEquals("주거", responseDto.getCategoryName());
  }
}