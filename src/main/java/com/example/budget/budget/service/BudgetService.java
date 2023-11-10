package com.example.budget.budget.service;

import com.example.budget.budget.dto.BudgetRequestDto;
import com.example.budget.budget.dto.BudgetRequestDto.BudgetEditRequestDto;
import com.example.budget.budget.dto.BudgetRequestDto.BudgetRecommendRequestDto;
import com.example.budget.budget.dto.BudgetResponseDto;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BudgetService {

  private static final Double MIN_PERCENTAGE_FOR_OTHERS = 10.0;
  private static final String OTHERS_CATEGORY_LABEL = "기타";

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

  @Transactional
  public BudgetResponseDto.BudgetEditResponseDto editBudget(
      Long budgetId, BudgetEditRequestDto requestDto) {
    // TODO: 2023/11/11 - 시큐리티 적용하면 변경하기
//     Member member = memberService.getMemberEntity(requestDto.getMemberId());
    Category category = categoryService.getCategoryId(requestDto.getCategoryId());

//    if (budgetRepository.existsByMemberAndCategory(member, category)) {
//      throw new CustomApiException("이미 존재하는 데이터입니다.");
//    }

    Budget budget = budgetRepository.findById(budgetId)
        .orElseThrow(() -> new CustomApiException("찾을 수 없는 예산 번호입니다."));

    budget.update(requestDto.getAmount(), category);
    return new BudgetEditResponseDto(budget);
  }

  @Transactional(readOnly = true)
  public BudgetRecommendResponseDto recommendBudget(BudgetRecommendRequestDto requestDto) {
    // TODO: 2023/11/11 - 시큐리티 적용하면 변경하기
//     Member member = memberService.getMemberEntity(requestDto.getMemberId());

    List<Budget> budgets = budgetRepository.findAllWithCategory();
    Map<Category, Double> categoryPercentages = calculateCategoryPercentages(budgets);
    return new BudgetRecommendResponseDto(
        sortBudgetAllocation(computeBudgetAllocation(requestDto.getAmount(), categoryPercentages)));
  }

  private Map<String, Long> computeBudgetAllocation(Long totalAmount,
      Map<Category, Double> categoryPercentages) {
    Map<String, Long> budgetAllocation = new HashMap<>();
    long otherCategoryAmount = 0L;

    for (Map.Entry<Category, Double> entry : categoryPercentages.entrySet()) {
      long allocatedAmount = Math.round(entry.getValue() / 100.0 * totalAmount);
      if (entry.getValue() <= MIN_PERCENTAGE_FOR_OTHERS) {
        otherCategoryAmount += allocatedAmount;
      } else {
        budgetAllocation.put(entry.getKey().getName(), allocatedAmount);
      }
    }

    if (otherCategoryAmount > 0) {
      budgetAllocation.put(OTHERS_CATEGORY_LABEL, otherCategoryAmount);
    }

    return budgetAllocation;
  }

  // 금액을 기준으로 내림차순 정렬
  private Map<String, Long> sortBudgetAllocation(Map<String, Long> budgetAllocation) {
    return budgetAllocation.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new));
  }

  // 전체 예산을 계산하는 메서드
  public Long calculateTotalBudget(List<Budget> budgets) {
    return budgets.stream().mapToLong(Budget::getAmount).sum();
  }

  // 카테고리별 예산 합계를 계산하는 메서드
  public Map<Category, Long> calculateCategoryTotal(List<Budget> budgets) {
    return budgets.stream().collect(
        Collectors.groupingBy(Budget::getCategory, Collectors.summingLong(Budget::getAmount)));
  }

  // 카테고리별 예산 비율을 계산하는 메서드
  public Map<Category, Double> calculateCategoryPercentages(List<Budget> budgets) {
    Long totalBudget = calculateTotalBudget(budgets);
    Map<Category, Long> categoryTotals = calculateCategoryTotal(budgets);

    Map<Category, Double> categoryPercentages = new HashMap<>();
    for (Map.Entry<Category, Long> entry : categoryTotals.entrySet()) {
      double percentage = 100.0 * entry.getValue() / totalBudget;
      categoryPercentages.put(entry.getKey(), percentage);
    }

    return categoryPercentages;
  }
}
