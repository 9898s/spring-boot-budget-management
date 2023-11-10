package com.example.budget.budget.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.budget.budget.dto.BudgetRequestDto.BudgetEditRequestDto;
import com.example.budget.budget.dto.BudgetRequestDto.BudgetRecommendRequestDto;
import com.example.budget.budget.dto.BudgetRequestDto.BudgetSetRequestDto;
import com.example.budget.budget.dto.BudgetResponseDto.BudgetEditResponseDto;
import com.example.budget.budget.dto.BudgetResponseDto.BudgetRecommendResponseDto;
import com.example.budget.budget.dto.BudgetResponseDto.BudgetSetResponseDto;
import com.example.budget.budget.entity.Budget;
import com.example.budget.budget.repository.BudgetRepository;
import com.example.budget.category.entity.Category;
import com.example.budget.category.service.CategoryService;
import com.example.budget.global.handler.exception.CustomApiException;
import com.example.budget.member.entity.Member;
import com.example.budget.member.service.MemberService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

  @Test
  void 이미_존재하는_예산설정_test() throws Exception {
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

    BudgetSetRequestDto requestDto = new BudgetSetRequestDto();
    requestDto.setAmount(1000L);
    requestDto.setMemberId(1L);
    requestDto.setCategoryId(1L);

    // stub 1
    when(memberService.getMemberEntity(anyLong())).thenReturn(hong);

    // stub 2
    when(categoryService.getCategoryId(anyLong())).thenReturn(home);

    // stub 3
    when(budgetRepository.existsByMemberAndCategory(any(), any())).thenReturn(true);

    // when & then
    CustomApiException exception = assertThrows(CustomApiException.class, () -> {
      budgetService.setBudget(requestDto);
    });

    assertEquals("이미 존재하는 데이터입니다.", exception.getMessage());
  }

  @Test
  void 예산수정_test() throws Exception {
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

    Category food = Category.builder()
        .id(2L)
        .name("식비")
        .build();

    Budget budget = Budget.builder()
        .id(1L)
        .amount(1000L)
        .member(hong)
        .category(home)
        .build();

    BudgetEditRequestDto requestDto = new BudgetEditRequestDto();
    requestDto.setAmount(2000L);
    requestDto.setCategoryId(2L);

    // stub 1
//    when(memberService.getMemberEntity(anyLong())).thenReturn(hong);

    // stub 2
    when(categoryService.getCategoryId(anyLong())).thenReturn(food);

    // stub 3
//    when(budgetRepository.existsByMemberAndCategory(any(), any())).thenReturn(false);

    // stub 4
    when(budgetRepository.findById(anyLong())).thenReturn(Optional.ofNullable(budget));

    // when
    BudgetEditResponseDto responseDto = budgetService.editBudget(1L, requestDto);

    // then
    assertEquals(2000L, responseDto.getAmount());
    assertEquals("식비", responseDto.getCategoryName());
  }

  @Test
  void 예산추천_test() throws Exception {
    // given
    Member kim = Member.builder().id(1L).account("kim").password("1234").build();
    Member lee = Member.builder().id(2L).account("lee").password("1234").build();

    Category home = Category.builder().id(1L).name("주거").build();
    Category food = Category.builder().id(2L).name("식비").build();
    Category traffic = Category.builder().id(3L).name("교통").build();

    Budget budget1 = Budget.builder().id(1L).amount(300L).member(kim).category(home).build();
    Budget budget2 = Budget.builder().id(2L).amount(300L).member(lee).category(home).build();
    Budget budget3 = Budget.builder().id(3L).amount(150L).member(kim).category(food).build();
    Budget budget4 = Budget.builder().id(4L).amount(150L).member(lee).category(food).build();
    Budget budget5 = Budget.builder().id(5L).amount(100L).member(kim).category(traffic).build();

    List<Budget> budgets = Arrays.asList(budget1, budget2, budget3, budget4, budget5);

    BudgetRecommendRequestDto requestDto = new BudgetRecommendRequestDto();
    requestDto.setAmount(10000L);
    requestDto.setMemberId(3L);

    // stub 1
//    when(memberService.getMemberEntity(anyLong())).thenReturn(hong);

    // stub 2
    when(budgetRepository.findAllWithCategory()).thenReturn(budgets);

    // when
    BudgetRecommendResponseDto responseDto = budgetService.recommendBudget(requestDto);

    // then
    assertEquals(6000L, responseDto.getCategoryRecommend().get("주거"));
    assertEquals(3000L, responseDto.getCategoryRecommend().get("식비"));
    assertEquals(1000L, responseDto.getCategoryRecommend().get("기타"));
  }
}