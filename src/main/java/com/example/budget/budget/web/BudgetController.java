package com.example.budget.budget.web;

import com.example.budget.budget.dto.BudgetRequestDto;
import com.example.budget.budget.dto.BudgetResponseDto;
import com.example.budget.budget.service.BudgetService;
import com.example.budget.global.dto.ResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/budget")
@RestController
public class BudgetController {

  private final BudgetService budgetService;

  @PostMapping
  public ResponseEntity<?> setBudget(
      @RequestBody @Valid BudgetRequestDto.BudgetSetRequestDto requestDto,
      BindingResult bindingResult) {
    BudgetResponseDto.BudgetSetResponseDto responseDto = budgetService.setBudget(requestDto);

    return new ResponseEntity<>(
        new ResponseDto<>(true, "예산 설정", responseDto), HttpStatus.CREATED);
  }

  @PutMapping("/{budgetId}")
  public ResponseEntity<?> editBudget(
      @PathVariable Long budgetId,
      @RequestBody @Valid BudgetRequestDto.BudgetEditRequestDto requestDto,
      BindingResult bindingResult) {
    BudgetResponseDto.BudgetEditResponseDto responseDto =
        budgetService.editBudget(budgetId, requestDto);

    return new ResponseEntity<>(
        new ResponseDto<>(true, "예산 수정", responseDto), HttpStatus.OK
    );
  }

  @GetMapping("/recommend")
  public ResponseEntity<?> recommendBudget(
      @RequestBody @Valid BudgetRequestDto.BudgetRecommendRequestDto requestDto,
      BindingResult bindingResult) {
    BudgetResponseDto.BudgetRecommendResponseDto responseDto =
        budgetService.recommendBudget(requestDto);
    return new ResponseEntity<>(
        new ResponseDto<>(true, "예산 추천", responseDto), HttpStatus.OK
    );
  }
}
