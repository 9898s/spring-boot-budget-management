package com.example.budget.budget.dto;

import com.example.budget.budget.entity.Budget;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class BudgetResponseDto {

  @Setter
  @Getter
  public static class BudgetSetResponseDto {

    private Long amount;
    private Long memberId;
    private String categoryName;

    public BudgetSetResponseDto(Budget budget) {
      this.amount = budget.getAmount();
      this.memberId = budget.getMember().getId();
      this.categoryName = budget.getCategory().getName();
    }
  }

  @Setter
  @Getter
  public static class BudgetEditResponseDto {

    private Long amount;
    private String categoryName;

    public BudgetEditResponseDto(Budget budget) {
      this.amount = budget.getAmount();
      this.categoryName = budget.getCategory().getName();
    }
  }

  @Setter
  @Getter
  public static class BudgetRecommendResponseDto {

    private Map<String, Long> categoryRecommend;

    public BudgetRecommendResponseDto(Map<String, Long> categoryAllocations) {
      this.categoryRecommend = categoryAllocations;
    }
  }
}
