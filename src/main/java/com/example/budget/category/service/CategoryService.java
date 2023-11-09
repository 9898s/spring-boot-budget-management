package com.example.budget.category.service;

import com.example.budget.category.dto.CategoryResponseDto.CategoryListResponseDto;
import com.example.budget.category.entity.Category;
import com.example.budget.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public CategoryListResponseDto getCategories() {
    List<Category> categories = categoryRepository.findAll();
    return new CategoryListResponseDto(categories);
  }
}
