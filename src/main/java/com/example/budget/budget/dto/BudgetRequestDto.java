package com.example.budget.budget.dto;

import com.example.budget.budget.entity.Budget;
import com.example.budget.category.entity.Category;
import com.example.budget.member.entity.Member;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class BudgetRequestDto {

  @Setter
  @Getter
  public static class BudgetSetRequestDto {

    @Positive(message = "양수 값만 입력해주시길 바랍니다.")
    private Long amount;

    // TODO: 2023/11/11 - 시큐리티 적용하면 지우기
    @NotNull(message = "회원 번호를 입력해주시길 바랍니다.")
    private Long memberId;

    @NotNull(message = "카테고리 번호를 입력해주시길 바랍니다.")
    private Long categoryId;

    public Budget toEntity(Member member, Category category) {
      return Budget.builder()
          .amount(amount)
          .member(member)
          .category(category)
          .build();
    }
  }

  @Setter
  @Getter
  public static class BudgetEditRequestDto {

    private Long amount;
    private Long categoryId;
  }

  @Setter
  @Getter
  public static class BudgetRecommendRequestDto {

    @Positive(message = "양수 값만 입력해주시길 바랍니다.")
    private Long amount;

    // TODO: 2023/11/11 - 시큐리티 적용하면 지우기
    @NotNull(message = "회원 번호를 입력해주시길 바랍니다.")
    private Long memberId;
  }
}
