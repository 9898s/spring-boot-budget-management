package com.example.budget.budget.service;

import com.example.budget.budget.dto.BudgetRequestDto;
import com.example.budget.budget.dto.BudgetResponseDto;
import com.example.budget.budget.dto.BudgetResponseDto.BudgetSetResponseDto;
import com.example.budget.budget.entity.Budget;
import com.example.budget.budget.repository.BudgetRepository;
import com.example.budget.category.entity.Category;
import com.example.budget.category.service.CategoryService;
import com.example.budget.global.handler.exception.CustomApiException;
import com.example.budget.member.entity.Member;
import com.example.budget.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BudgetService {

  private final BudgetRepository budgetRepository;
  private final MemberService memberService;
  private final CategoryService categoryService;

  @Transactional
  public BudgetResponseDto.BudgetSetResponseDto setBudget(
      BudgetRequestDto.BudgetSetRequestDto requestDto) {
    Member member = memberService.getMemberEntity(requestDto.getMemberId());
    Category category = categoryService.getCategoryId(requestDto.getCategoryId());

    if (budgetRepository.existsByMemberAndCategory(member, category)) {
      throw new CustomApiException("이미 존재하는 데이터입니다.");
    }

    Budget budget = budgetRepository.save(requestDto.toEntity(member, category));
    return new BudgetSetResponseDto(budget);
  }
}
