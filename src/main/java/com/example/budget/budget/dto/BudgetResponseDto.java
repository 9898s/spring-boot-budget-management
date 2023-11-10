package com.example.budget.budget.dto;

import com.example.budget.budget.entity.Budget;
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
}
