package com.example.budget.category.web;

import com.example.budget.category.dto.CategoryResponseDto.CategoryListResponseDto;
import com.example.budget.category.service.CategoryService;
import com.example.budget.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/category")
@RestController
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<?> getCategories() {
    CategoryListResponseDto categories = categoryService.getCategories();
    return new ResponseEntity<>(
        new ResponseDto<>(true, "모든 카테고리 목록", categories), HttpStatus.OK
    );
  }
}
